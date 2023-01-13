package pool

import Serializer.given
import Validator.*

final class Dispatcher(store: Store, emailer: Emailer):
  def dispatch[E <: Event](command: Command): Event =
    if authorize(command) && validate(command) then command match
      case Register(emailAddress)          => register(emailAddress)
      case Login(emailAddress, pin)        => login(emailAddress, pin)
      case Deactivate(license)             => deactivateAccount(license)
      case Reactivate(license)             => reactivateAccount(license)
      case ListPools(_)                    => listPools
      case SavePool(_, pool)               => savePool(pool)
      case ListCleanings(_, poolId)        => listCleanings(poolId)
      case SaveCleaning(_, cleaning)       => saveCleaning(cleaning)
      case ListMeasurements(_, poolId)     => listMeasurements(poolId)
      case SaveMeasurement(_, measurement) => saveMeasurement(measurement)
      case ListChemicals(_, poolId)        => listChemicals(poolId)
      case SaveChemical(_, chemical)       => saveChemical(chemical)
    else Fault(s"Invalid command: $command")

  private val subject = "Account Registration"

  private def authorize(command: Command): Boolean =
    command match
      case license: License          => if license.isLicense then store.isAuthorized(license.license) else false
      case Register(_) | Login(_, _) => true

  private def validate(command: Command): Boolean = command.isValid

  private def register(emailAddress: String): Registered | Fault =
    val account = Account(emailAddress = emailAddress)
    val sent = email(account.emailAddress, account.pin)
    if sent then Registered( store.register(account) )
    else Fault(s"Registration failed for: $emailAddress")

  private def email(emailAddress: String, pin: String): Boolean =
    val recipients = List(emailAddress)
    val message = s"Save this pin: ${pin} Then delete this email!"
    emailer.send(recipients, subject, message)

  private def login(emailAddress: String, pin: String): LoggedIn | Fault =
    val optionalAccount = store.login(emailAddress, pin)
    if optionalAccount.isDefined then LoggedIn(optionalAccount.get)
    else Fault(s"Invalid email address: $emailAddress and/or pin: $pin")

  private def deactivateAccount(license: String): Deactivated | Fault =
    val optionalAccount = store.deactivateAccount(license)
    if optionalAccount.isDefined then Deactivated(optionalAccount.get)
    else Fault(s"Invalid license: $license")

  private def reactivateAccount(license: String): Reactivated | Fault =
    val optionalAccount = store.reactivateAccount(license)
    if optionalAccount.isDefined then Reactivated(optionalAccount.get)
    else Fault(s"Invalid license: $license")

  private def listPools: PoolsListed = PoolsListed(store.listPools())

  private def savePool(pool: Pool): PoolSaved =
    val id = if pool.id == 0 then store.addPool(pool) else store.updatePool(pool)
    PoolSaved(id)

  private def listCleanings(poolId: Long): CleaningsListed = CleaningsListed( store.listCleanings(poolId) )

  private def saveCleaning(cleaning: Cleaning): CleaningSaved =
    for
      id <- if cleaning.id == 0 then store.addCleaning(cleaning) else store.updateCleaning(cleaning)
    yield CleaningSaved(id)

  private def listMeasurements: MeasurementsListed =
    for
      measurements <- store.listMeasurements
    yield MeasurementsListed(measurements)

  private def saveMeasurement(measurement: Measurement): MeasurementSaved =
    for
      id <- if measurement.id == 0 then store.addMeasurement(measurement) else store.updateMeasurement(measurement)
    yield MeasurementSaved(id)

  private def listChemicals: ChemicalsListed =
    for
      chemicals <- store.listChemicals
    yield ChemicalsListed(chemicals)

  private def saveChemical(chemical: Chemical): TaskChemicalSaved =
    for
      id <- if chemical.id == 0 then store.addChemical(chemical) else store.updateChemical(chemical)
    yield ChemicalSaved(id)