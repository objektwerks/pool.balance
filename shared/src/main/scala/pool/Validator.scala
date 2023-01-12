package pool

object Validator:
  extension (value: String)
    def isLicense: Boolean = if value.nonEmpty && value.length == 36 then true else false
    def isPin: Boolean = value.length == 7
    def isEmailAddress: Boolean = value.nonEmpty && value.length >= 3 && value.contains("@")

  extension (command: Command)
    def isValid: Boolean =
      command match
        case register @ Register(emailAddress)       => register.isValid
        case login @ Login(_, _)                     => login.isValid
        case deactivate @ Deactivate(_)              => deactivate.isValid
        case reactivate @ Reactivate(_)              => reactivate.isValid
        case listPools @ ListPools(_)                => listPools.isValid
        case savePool @ SavePool(_, _)               => savePool.isValid
        case listCleanings @ ListCleanings(_)        => listCleanings.isValid
        case saveCleaning @ SaveCleaning(_, _)       => saveCleaning.isValid
        case listMeasurements @ ListMeasurements(_)  => listMeasurements.isValid
        case saveMeasurement @ SaveMeasurement(_, _) => saveMeasurement.isValid
        case listChemicals @ ListChemicals(_)        => listChemicals.isValid
        case saveChemical @ SaveChemical(_, _)       => saveChemical.isValid

  extension (register: Register)
    def isValid: Boolean = register.emailAddress.isEmailAddress

  extension (login: Login)
    def isValid: Boolean = login.emailAddress.isEmailAddress && login.pin.isPin

  extension (deactivate: Deactivate)
    def isValid: Boolean = deactivate.license.isLicense

  extension (reactivate: Reactivate)
    def isValid: Boolean = reactivate.license.isLicense

  extension (listPools: ListPools)
    def isValid: Boolean = true

  extension (savePool: SavePool)
    def isValid: Boolean = savePool.pool.isValid

  extension (listCleanings: ListCleanings)
    def isValid: Boolean = true

  extension (saveCleaning: SaveCleaning)
    def isValid: Boolean = saveCleaning.cleaning.isValid

  extension (listMeasurements: ListMeasurements)
    def isValid: Boolean = true

  extension (saveMeasurement: SaveMeasurement)
    def isValid: Boolean = saveMeasurement.measurement.isValid

  extension (listChemicals: ListChemicals)
    def isValid: Boolean = true

  extension (saveChemical: SaveChemical)
    def isValid: Boolean = saveChemical.chemical.isValid

  extension  (license: License)
    def isLicense: Boolean = license.license.isLicense

  extension (account: Account)
    def isActivated: Boolean =
      account.id >= 0 &&
      account.license.isLicense &&
      account.pin.isPin &&
      account.activated.nonEmpty &&
      account.deactivated.isEmpty
    def isDeactivated: Boolean =
      account.license.isLicense &&
      account.pin.isPin &&
      account.activated.isEmpty &&
      account.deactivated.nonEmpty

  extension (pool: Pool)
    def isValid =
      pool.id >= 0 &&
      pool.name.nonEmpty &&
      pool.volume > 1000 &&
      pool.unit.nonEmpty

  extension (cleaning: Cleaning)
    def isValid: Boolean =
      cleaning.id >= 0 &&
      cleaning.poolId > 0 &&
      cleaning.cleaned.nonEmpty

  extension (measurement: Measurement)
    def isValid: Boolean =
      import Measurement.*

      measurement.id >= 0 &&
      measurement.poolId > 0 &&
      totalChlorineRange.contains(measurement.totalChlorine) &&
      freeChlorineRange.contains(measurement.freeChlorine) &&
      combinedChlorineRange.contains(measurement.combinedChlorine) &&
      (measurement.ph >= 6.2 && measurement.ph <= 8.4) &&
      calciumHardnessRange.contains(measurement.calciumHardness) &&
      totalAlkalinityRange.contains(measurement.totalAlkalinity) &&
      cyanuricAcidRange.contains(measurement.cyanuricAcid) &&
      totalBromineRange.contains(measurement.totalBromine) &&
      saltRange.contains(measurement.salt) &&
      temperatureRange.contains(measurement.temperature) &&
      measurement.measured.nonEmpty

  extension (chemical: Chemical)
    def isValid: Boolean =
      chemical.id >= 0 &&
      chemical.poolId > 0 &&
      chemical.typeof.nonEmpty &&
      chemical.amount > 0.00 &&
      chemical.unit.nonEmpty
      chemical.added.nonEmpty