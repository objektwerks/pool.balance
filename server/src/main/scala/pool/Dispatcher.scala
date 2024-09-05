package pool

import ox.{IO, supervised}
import ox.resilience.{retry, RetryConfig}

import scala.concurrent.duration.*
import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

final class Dispatcher(store: Store, emailer: Emailer):
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
        try
          supervised:
            retry( RetryConfig.delay(1, 100.millis) )(
              if store.isAuthorized(license.license) then Authorized
              else Unauthorized(s"Unauthorized: $command")
            )
        catch
          case NonFatal(error) => Unauthorized(s"Unauthorized: $command, cause: $error")
      case Register(_) | Login(_, _) => Authorized

  private def sendEmail(emailAddress: String, message: String): Unit =
    val recipients = List(emailAddress)
    emailer.send(recipients, message)

  private def register(emailAddress: String)(using IO): Event =
    try
      supervised:
        val account = Account(emailAddress = emailAddress)
        val message = s"Your new pin is: ${account.pin}\n\nWelcome aboard!"
        retry( RetryConfig.delay(1, 600.millis) )( sendEmail(account.emailAddress, message) )
        Registered( store.register(account) )
    catch
      case NonFatal(error) => Fault(s"Registration failed for: $emailAddress, because: ${error.getMessage}")

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
    try
      PoolsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listPools(license) )
      )
    catch
      case NonFatal(error) => Fault("List pools failed:", error)

  private def savePool(pool: Pool)(using IO): Event =
    try
      PoolSaved(
        supervised:
          if pool.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addPool(pool) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updatePool(pool) )
      )
    catch
      case NonFatal(error) => Fault("Save pool failed:", error)

  private def listCleanings(poolId: Long)(using IO): Event =
    try
      CleaningsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listCleanings(poolId) )
      )
    catch
      case NonFatal(error) => Fault("List cleanings failed:", error)

  private def saveCleaning(cleaning: Cleaning)(using IO): Event =
    try
      CleaningSaved(
        supervised:
          if cleaning.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addCleaning(cleaning) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateCleaning(cleaning) )
      )
    catch
      case NonFatal(error) => Fault("Save cleaning failed:", error)

  private def listMeasurements(poolId: Long)(using IO): Event =
    try
      MeasurementsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listMeasurements(poolId) )
      )
    catch
      case NonFatal(error) => Fault("List measurements failed:", error)

  private def saveMeasurement(measurement: Measurement)(using IO): Event =
    try
      MeasurementSaved(
        supervised:
          if measurement.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addMeasurement(measurement) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateMeasurement(measurement) )
      )
    catch
      case NonFatal(error) => Fault("Save measurement failed:", error)

  private def listChemicals(poolId: Long)(using IO): Event =
    try
      ChemicalsListed(
        supervised:
          retry( RetryConfig.delay(1, 100.millis) )( store.listChemicals(poolId) )
      )
    catch
      case NonFatal(error) => Fault("List chemicals failed:", error)

  private def saveChemical(chemical: Chemical)(using IO): Event =
    try
      ChemicalSaved(
        supervised:
          if chemical.id == 0 then retry( RetryConfig.delay(1, 100.millis) )( store.addChemical(chemical) )
          else retry( RetryConfig.delay(1, 100.millis) )( store.updateChemical(chemical) )
      )
    catch
      case NonFatal(error) => Fault("Save chemical failed:", error)

  private def addFault(fault: Fault)(using IO): Event =
    try
      supervised:
        retry( RetryConfig.delay(1, 100.millis) )( store.addFault(fault) )
        FaultAdded()
    catch
      case NonFatal(error) => Fault("Add fault failed:", error)