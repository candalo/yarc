package github.com.candalo.hashmobilechallenge.domain.model

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class PostMedia(
    val thumbnailUrl: String?,
    val mediaUrl: String?
) : Serializable