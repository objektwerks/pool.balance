package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

object Serializer:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]

  given JsonValueCodec[Command] = JsonCodecMaker.make[Command]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[License] = JsonCodecMaker.make[License]
  given JsonValueCodec[Register] = JsonCodecMaker.make[Register]
  given JsonValueCodec[Login] = JsonCodecMaker.make[Login]
  given JsonValueCodec[Deactivate] = JsonCodecMaker.make[Deactivate]
  given JsonValueCodec[Reactivate] = JsonCodecMaker.make[Reactivate]
  given JsonValueCodec[ListPools] = JsonCodecMaker.make[ListPools]
  given JsonValueCodec[SavePool] = JsonCodecMaker.make[SavePool]
  given JsonValueCodec[ListCleanings] = JsonCodecMaker.make[ListCleanings]
  given JsonValueCodec[SaveCleaning] = JsonCodecMaker.make[SaveCleaning]
  given JsonValueCodec[ListMeasurements] = JsonCodecMaker.make[ListMeasurements]
  given JsonValueCodec[SaveMeasurement] = JsonCodecMaker.make[SaveMeasurement]
  given JsonValueCodec[ListChemicals] = JsonCodecMaker.make[ListChemicals]
  given JsonValueCodec[SaveChemical] = JsonCodecMaker.make[SaveChemical]

  given JsonValueCodec[Event] = JsonCodecMaker.make[Event]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Registered] = JsonCodecMaker.make[Registered]
  given JsonValueCodec[LoggedIn] = JsonCodecMaker.make[LoggedIn]
  given JsonValueCodec[Deactivated] = JsonCodecMaker.make[Deactivated]
  given JsonValueCodec[Reactivated] = JsonCodecMaker.make[Reactivated]
  given JsonValueCodec[PoolsListed] = JsonCodecMaker.make[PoolsListed]
  given JsonValueCodec[PoolSaved] = JsonCodecMaker.make[PoolSaved]
  given JsonValueCodec[CleaningsListed] = JsonCodecMaker.make[CleaningsListed]
  given JsonValueCodec[CleaningSaved] = JsonCodecMaker.make[CleaningSaved]
  given JsonValueCodec[MeasurementsListed] = JsonCodecMaker.make[MeasurementsListed]
  given JsonValueCodec[MeasurementSaved] = JsonCodecMaker.make[MeasurementSaved]
  given JsonValueCodec[ChemicalsListed] = JsonCodecMaker.make[ChemicalsListed]
  given JsonValueCodec[ChemicalSaved] = JsonCodecMaker.make[ChemicalSaved]
  given JsonValueCodec[Fault] = JsonCodecMaker.make[Fault]