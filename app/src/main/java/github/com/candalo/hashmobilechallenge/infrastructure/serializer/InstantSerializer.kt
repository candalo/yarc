package github.com.candalo.hashmobilechallenge.infrastructure.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

internal object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(InstantSerializer::class.java.name, PrimitiveKind.DOUBLE)

    override fun deserialize(decoder: Decoder): Instant =
            Instant.ofEpochSecond(decoder.decodeDouble().toLong())

    override fun serialize(encoder: Encoder, value: Instant) =
            encoder.encodeDouble(value.toEpochMilli().toDouble())
}