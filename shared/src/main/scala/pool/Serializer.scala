package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

object Serializer:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]( CodecMakerConfig.withDiscriminatorFieldName(None) )

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
  given JsonValueCodec[PoolsListed] = JsonCodecMaker.make[PoolsListed]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[PoolSaved] = JsonCodecMaker.make[PoolSaved]( CodecMakerConfig.withDiscriminatorFieldName(None) )
