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
  given JsonValueCodec[License] = JsonCodecMaker.make[License]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Register] = JsonCodecMaker.make[Register]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Login] = JsonCodecMaker.make[Login]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Deactivate] = JsonCodecMaker.make[Deactivate]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Reactivate] = JsonCodecMaker.make[Reactivate]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ListPools] = JsonCodecMaker.make[ListPools]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[SavePool] = JsonCodecMaker.make[SavePool]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ListCleanings] = JsonCodecMaker.make[ListCleanings]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[SaveCleaning] = JsonCodecMaker.make[SaveCleaning]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ListMeasurements] = JsonCodecMaker.make[ListMeasurements]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[SaveMeasurement] = JsonCodecMaker.make[SaveMeasurement]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ListChemicals] = JsonCodecMaker.make[ListChemicals]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[SaveChemical] = JsonCodecMaker.make[SaveChemical]( CodecMakerConfig.withDiscriminatorFieldName(None) )

  given JsonValueCodec[Event] = JsonCodecMaker.make[Event]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Registered] = JsonCodecMaker.make[Registered]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[LoggedIn] = JsonCodecMaker.make[LoggedIn]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Deactivated] = JsonCodecMaker.make[Deactivated]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Reactivated] = JsonCodecMaker.make[Reactivated]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[PoolsListed] = JsonCodecMaker.make[PoolsListed]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[PoolSaved] = JsonCodecMaker.make[PoolSaved]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[CleaningsListed] = JsonCodecMaker.make[CleaningsListed]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[CleaningSaved] = JsonCodecMaker.make[CleaningSaved]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[MeasurementsListed] = JsonCodecMaker.make[MeasurementsListed]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[MeasurementSaved] = JsonCodecMaker.make[MeasurementSaved]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ChemicalsListed] = JsonCodecMaker.make[ChemicalsListed]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[ChemicalSaved] = JsonCodecMaker.make[ChemicalSaved]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Fault] = JsonCodecMaker.make[Fault]( CodecMakerConfig.withDiscriminatorFieldName(None) )