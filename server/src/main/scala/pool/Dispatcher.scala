package pool

import com.typesafe.scalalogging.LazyLogging

import scala.util.Try
import scala.util.control.NonFatal

import Serializer.given
import Validator.*

final class Dispatcher(store: Store,
                       emailer: Emailer) extends LazyLogging:
  def dispatch[E <: Event](command: Command): Event =
    Try {
      if command.isValid && isAuthorized(command) then command match
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
      else Fault(s"Failed to process invalid command: $command")
    }.recover {
      case NonFatal(error) =>
        val message = s"Failed to process command: $command, because: ${error.getMessage}"
        logger.error(message)
        Fault(message)
    }.get

  private val subject = "Account Registration"

  private def isAuthorized(command: Command): Boolean =
    command match
      case license: License          => Try { store.isAuthorized(license.license) }.getOrElse(false)
      case Register(_) | Login(_, _) => true

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
    val message = s"<p>Save this pin: <b>${pin}</b> in a safe place; then delete this email.</p>"
    emailer.send(recipients, subject, message)

  private def login(emailAddress: String, pin: String): Event =
    val optionalAccount = Try { store.login(emailAddress, pin) }.getOrElse(None)
    if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
    else Fault(s"Failed to login due to invalid email address: $emailAddress and/or pin: $pin")

  private def deactivateAccount(license: String): Event =
    val optionalAccount = Try { store.deactivateAccount(license) }.getOrElse(None)
    if optionalAccount.isDefined then Deactivated(optionalAccount.get)
    else Fault(s"Failed to deactivated account due to invalid license: $license")

  private def reactivateAccount(license: String): Event =
    val optionalAccount = Try { store.reactivateAccount(license) }.getOrElse(None)
    if optionalAccount.isDefined then Reactivated(optionalAccount.get)
    else Fault(s"Failed to reactivate account due to invalid license: $license")

  private def listPools(license: String): Event = PoolsListed(store.listPools(license))

  private def savePool(pool: Pool): PoolSaved =
    PoolSaved(
      if pool.id == 0 then store.addPool(pool)
      else store.updatePool(pool)
    )

  private def listCleanings(poolId: Long): Event = CleaningsListed( store.listCleanings(poolId) )

  private def saveCleaning(cleaning: Cleaning): CleaningSaved =
    CleaningSaved(
      if cleaning.id == 0 then store.addCleaning(cleaning)
      else store.updateCleaning(cleaning)
    )

  private def listMeasurements(poolId: Long): Event = MeasurementsListed( store.listMeasurements(poolId) )

  private def saveMeasurement(measurement: Measurement): Event =
    MeasurementSaved(
      if measurement.id == 0 then store.addMeasurement(measurement)
      else store.updateMeasurement(measurement)
    )

  private def listChemicals(poolId: Long): Event = ChemicalsListed( store.listChemicals(poolId) )

  private def saveChemical(chemical: Chemical): Event =
    ChemicalSaved(
      if chemical.id == 0 then store.addChemical(chemical)
      else store.updateChemical(chemical)
    )