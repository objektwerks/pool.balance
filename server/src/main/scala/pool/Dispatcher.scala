package pool

import ox.{IO, supervised}
import ox.resilience.{retry, RetryConfig}

import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

final class Dispatcher(store: Store,
                       emailer: Emailer):
  def dispatch(command: Command): Event =
    IO.unsafe:
      command.isValid match
        case false => addFault( Fault(s"Invalid command: $command") )
        case true =>
          isAuthorized(command) match
            case Unauthorized(cause) => addFault( Fault(cause) )
            case Authorized =>
              command match
                case Register(emailAddress)          => register(emailAddress)
                case Login(emailAddress, pin)        => login(emailAddress, pin)
                case Deactivate(license)             => deactivateAccount(license)
                case Reactivate(license)             => reactivateAccount(license)
                case ListPools(license)              => listPools(license)
                case SavePool(_, pool)               => savePool(pool)
                case ListCleanings(_, poolId)        => listCleanings(poolId)
                case SaveCleaning(_, cleaning)       => saveCleaning(cleaning)
                case ListMeasurements(_, poolId)     => listMeasurements(poolId)
                case SaveMeasurement(_, measurement) => saveMeasurement(measurement)
                case ListChemicals(_, poolId)        => listChemicals(poolId)
                case SaveChemical(_, chemical)       => saveChemical(chemical)
                case AddFault(_, fault)              => addFault(fault)

  private def isAuthorized(command: Command)(using IO): Security =
    command match
      case license: License =>
        Try:
          supervised:
            retry( RetryConfig.delay(1, 100.millis) )(
              if store.isAuthorized(license.license) then Authorized
              else Unauthorized(s"Unauthorized: $command")
            )
        .recover:
          case NonFatal(error) => Unauthorized(s"Unauthorized: $command, cause: $error")
        .get
      case Register(_) | Login(_, _) => Authorized

  private def sendEmail(emailAddress: String, message: String): Unit =
    val recipients = List(emailAddress)
    emailer.send(recipients, message)

  private def register(emailAddress: String)(using IO): Event =
    Try:
      supervised:
        val account = Account(emailAddress = emailAddress)
        val message = s"Your new pin is: ${account.pin}\n\nWelcome aboard!"
        retry( RetryConfig.delay(1, 600.millis) )( sendEmail(account.emailAddress, message) )
        Registered( store.register(account) )
    .recover:
      case NonFatal(error) => Fault(s"Registration failed for: $emailAddress, because: ${error.getMessage}")
    .get

  private def login(emailAddress: String, pin: String)(using IO): Event =
    Try:
      supervised:
        retry( RetryConfig.delay(1, 100.millis) )( store.login(emailAddress, pin) )
    .fold(
      error => Fault("Login failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
        else Fault(s"Login failed for email address: $emailAddress and pin: $pin")
    )

  private def deactivateAccount(license: String)(using IO): Event =
    Try:
      supervised:
        retry( RetryConfig.delay(1, 100.millis) )( store.deactivateAccount(license) )
    .fold(
      error => Fault("Deactivate account failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then Deactivated(optionalAccount.get)
        else Fault(s"Deactivate account failed for license: $license")
    )

  private def reactivateAccount(license: String)(using IO): Event =
    Try:
      supervised:
        retry( RetryConfig.delay(1, 100.millis) )( store.reactivateAccount(license) )
    .fold(
      error => Fault("Reactivate account failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then Reactivated(optionalAccount.get)
        else Fault(s"Reactivate account failed for license: $license")
    )

  private def listPools(license: String)(using IO): Event =
    Try:
      PoolsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listPools(license) )
      )
    .recover:
      case NonFatal(error) => Fault("List pools failed:", error)
    .get

  private def savePool(pool: Pool)(using IO): Event =
    Try:
      PoolSaved(
        supervised:
          if pool.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addPool(pool) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updatePool(pool) )
      )
    .recover:
      case NonFatal(error) => Fault("Save pool failed:", error)
    .get

  private def listCleanings(poolId: Long)(using IO): Event =
    Try:
      CleaningsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listCleanings(poolId) )
      )
    .recover:
      case NonFatal(error) => Fault("List cleanings failed:", error)
    .get

  private def saveCleaning(cleaning: Cleaning)(using IO): Event =
    Try:
      CleaningSaved(
        supervised:
          if cleaning.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addCleaning(cleaning) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateCleaning(cleaning) )
      )
    .recover:
      case NonFatal(error) => Fault("Save cleaning failed:", error)
    .get

  private def listMeasurements(poolId: Long)(using IO): Event =
    Try:
      MeasurementsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listMeasurements(poolId) )
      )
    .recover:
      case NonFatal(error) => Fault("List measurements failed:", error)
    .get

  private def saveMeasurement(measurement: Measurement)(using IO): Event =
    Try:
      MeasurementSaved(
        supervised:
          if measurement.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addMeasurement(measurement) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateMeasurement(measurement) )
      )
    .recover:
      case NonFatal(error) => Fault("Save measurement failed:", error)
    .get

  private def listChemicals(poolId: Long)(using IO): Event =
    Try:
      ChemicalsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listChemicals(poolId) )
      )
    .recover:
      case NonFatal(error) => Fault("List chemicals failed:", error)
    .get

  private def saveChemical(chemical: Chemical)(using IO): Event =
    Try:
      ChemicalSaved(
        supervised:
          if chemical.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addChemical(chemical) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateChemical(chemical) )
      )
    .recover:
      case NonFatal(error) => Fault("Save chemical failed:", error)
    .get

  private def addFault(fault: Fault): Event =
    Try {
      store.addFault(fault)
      FaultAdded()
    }.recover { case NonFatal(error) => Fault("Add fault failed:", error) }
     .get
