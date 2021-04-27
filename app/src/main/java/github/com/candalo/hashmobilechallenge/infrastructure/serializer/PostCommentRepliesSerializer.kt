package github.com.candalo.hashmobilechallenge.infrastructure.serializer

import github.com.candalo.hashmobilechallenge.infrastructure.model.PostDetailsResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer

internal object PostCommentRepliesSerializer : KSerializer<PostDetailsResponse?> {
    override val descriptor: SerialDescriptor
        get() = PostDetailsResponse.serializer().descriptor

    override fun deserialize(decoder: Decoder): PostDetailsResponse? {
        return try {
            if (decoder.decodeString().isEmpty()) {
                return null
            }
            return decoder.decodeSerializableValue(PostDetailsResponse.serializer())
        } catch (e: Exception) {
            decoder.decodeSerializableValue(PostDetailsResponse.serializer())
        }
    }

    override fun serialize(encoder: Encoder, value: PostDetailsResponse?) {
        PostDetailsResponse.serializer().nullable.serialize(encoder, value)
    }
}