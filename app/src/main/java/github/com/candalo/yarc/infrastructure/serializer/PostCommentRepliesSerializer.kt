package github.com.candalo.yarc.infrastructure.serializer

import github.com.candalo.yarc.infrastructure.model.PostDetailsResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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