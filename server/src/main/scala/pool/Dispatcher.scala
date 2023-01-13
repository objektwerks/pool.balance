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
      case ListCleanings(_)                => listCleanings
      case SaveCleaning(_, cleaning)       => saveCleaning(cleaning)
      case ListMeasurements(_)             => listMeasurements
      case SaveMeasurement(_, measurement) => saveMeasurement(measurement)
      case ListChemicals(_)                => listChemicals
      case SaveChemical(_, chemical)       => saveChemical(chemical)
    else Fault(s"Invalid command: $command")

  private val subject = "Account Registration"

  private def authorize(command: Command): Boolean =
    command match
      case license: License          => if license.isLicense then store.authorize(license.license) else false
      case Register(_) | Login(_, _) => true

  private def validate(command: Command): Boolean = command.isValid

  private def register(emailAddress: String): Registered | Fault =
    val account = Account(emailAddress = emailAddress)
    val sent = email(account.emailAddress, account.pin)
    if sent then
      for
        id    <- store.register(account)
      yield Registered( account.copy(id = id) )
    else Fault(s"Registration failed for: $emailAddress")

  private def email(emailAddress: String, pin: String): Boolean =
    val recipients = List(emailAddress)
    val message = s"Save this pin: ${pin} Then delete this email!"
    emailer.send(recipients, subject, message)

  private def login(emailAddress: String, pin: String): LoggedIn | Fault =
    for
      option <- store.login(emailAddress, pin)
    yield if option.isDefined then LoggedIn(option.get) else Fault(s"Invalid pin: $pin")

  private def deactivateAccount(license: String): Deactivated =
    for
      account <- store.deactivateAccount(license)
    yield Deactivated(account)

  private def reactivateAccount(license: String): Reactivated =
    for
      account <- store.reactivateAccount(license)
    yield Reactivated(account)

  private def listPools: PoolsListed =
    for
      pools <- store.listPools
    yield PoolsListed(pools)

  private def savePool(pool: Pool): PoolSaved =
    for
      id <- if pool.id == 0 then store.addPool(pool) else store.updatePool(pool)
    yield PoolSaved(id)

  private def listCleanings: CleaningsListed =
    for
      cleanings <- store.listCleanings
    yield CleaningsListed(cleanings)

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