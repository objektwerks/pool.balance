package pool

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

object Serializer:
  given personCodec: JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  