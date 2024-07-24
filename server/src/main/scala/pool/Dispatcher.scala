package pool

import scala.util.Try
import scala.util.control.NonFatal

import Validator.*

sealed trait Security
case object Authorized extends Security
final case class Unauthorized(cause: String) extends Security

final class Dispatcher(store: Store, emailer: Emailer):
  def dispatch[E <: Event](command: Command): Event =
    if !command.isValid then store.addFault( Fault(s"Command is invalid: $command") )
    
    isAuthorized(command) match
      case Authorized(isAuthorized) => if !isAuthorized then store.addFault( Fault(s"License is unauthorized: $command") )
      case fault @ Fault(_, _) => store.addFault(fault)
      case _ =>
        
    val event = command match
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

    event match
      case fault @ Fault(_, _) => store.addFault(fault)
      case _ => event

  private def isAuthorized(command: Command): Event =
    command match
      case license: License =>
        Try {
          Authorized( store.isAuthorized(license.license) )
        }.recover { case NonFatal(error) => Fault(s"Authorization failed: $error") }
         .get
      case Register(_) | Login(_, _) => Authorized(true)

  private def register(emailAddress: String): Event =
    Try {
      val account = Account(emailAddress = emailAddress)
      if store.isEmailAddressUnique(emailAddress) then
        email(account.emailAddress, account.pin)
        Registered( store.register(account) )
      else Fault(s"Registration failed because: $emailAddress is already registered.")
    }.recover { case NonFatal(error) => Fault(s"Registration failed for: $emailAddress, because: ${error.getMessage}") }
     .get

  private def email(emailAddress: String, pin: String): Unit =
    val recipients = List(emailAddress)
    val message = s"<p>Your new pin is: <b>${pin}</b></p><p>Welcome aboard!</p>"
    emailer.send(recipients, message)

  private def login(emailAddress: String, pin: String): Event =
    Try { store.login(emailAddress, pin) }.fold(
      error => Fault("Login failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
        else Fault(s"Login failed for email address: $emailAddress and pin: $pin")
    )

  private def deactivateAccount(license: String): Event =
    Try { store.deactivateAccount(license) }.fold(
      error => Fault("Deactivate account failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then Deactivated(optionalAccount.get)
        else Fault(s"Deactivate account failed for license: $license")
    )

  private def reactivateAccount(license: String): Event =
    Try { store.reactivateAccount(license) }.fold(
      error => Fault("Reactivate account failed:", error),
      optionalAccount =>
        if optionalAccount.isDefined then Reactivated(optionalAccount.get)
        else Fault(s"Reactivate account failed for license: $license")
    )

  private def listPools(license: String): Event =
    Try {
      PoolsListed(store.listPools(license))
    }.recover { case NonFatal(error) => Fault("List pools failed:", error) }
     .get

  private def savePool(pool: Pool): Event =
    Try {
      PoolSaved(
        if pool.id == 0 then store.addPool(pool)
        else store.updatePool(pool)
      )
    }.recover { case NonFatal(error) => Fault("Save pool failed:", error) }
     .get

  private def listCleanings(poolId: Long): Event =
    Try {
      CleaningsListed( store.listCleanings(poolId) )
    }.recover { case NonFatal(error) => Fault("List cleanings failed:", error) }
     .get

  private def saveCleaning(cleaning: Cleaning): Event =
    Try {
      CleaningSaved(
        if cleaning.id == 0 then store.addCleaning(cleaning)
        else store.updateCleaning(cleaning)
      )
    }.recover { case NonFatal(error) => Fault("Save cleaning failed:", error) }
     .get

  private def listMeasurements(poolId: Long): Event =
    Try {
      MeasurementsListed( store.listMeasurements(poolId) )
    }.recover { case NonFatal(error) => Fault("List measurements failed:", error) }
     .get

  private def saveMeasurement(measurement: Measurement): Event =
    Try {
      MeasurementSaved(
        if measurement.id == 0 then store.addMeasurement(measurement)
        else store.updateMeasurement(measurement)
      )
    }.recover { case NonFatal(error) => Fault("Save measurement failed:", error) }
     .get

  private def listChemicals(poolId: Long): Event =
    Try {
      ChemicalsListed( store.listChemicals(poolId) )
    }.recover { case NonFatal(error) => Fault("List chemicals failed:", error) }
     .get    

  private def saveChemical(chemical: Chemical): Event =
    Try {
      ChemicalSaved(
        if chemical.id == 0 then store.addChemical(chemical)
        else store.updateChemical(chemical)
      )
    }.recover { case NonFatal(error) => Fault("Save chemical failed:", error) }
     .get

  private def addFault(fault: Fault): Event =
    Try {
      store.addFault(fault)
      FaultAdded()
    }.recover { case NonFatal(error) => Fault("Add fault failed:", error) }
     .get