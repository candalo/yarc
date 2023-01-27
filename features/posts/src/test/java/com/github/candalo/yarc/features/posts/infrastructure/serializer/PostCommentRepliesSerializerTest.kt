package com.github.candalo.yarc.features.posts.infrastructure.serializer

import com.github.candalo.yarc.features.posts.infrastructure.model.PostDetailsResponse
import com.github.candalo.yarc.features.posts.infrastructure.postCommentRepliesJson
import com.github.candalo.yarc.features.posts.infrastructure.postDetailsResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

@Serializable
private data class PostCommentRepliesStub(
    @Serializable(PostCommentRepliesSerializer::class)
    @SerialName("replies")
    val replies: PostDetailsResponse?
)

internal class PostCommentRepliesSerializerTest {
    @Test
    fun `serialize should return correct value`() {
        val model = PostCommentRepliesStub(postDetailsResponse)
        val json = Json.encodeToString(model)

        assertThat(json).isNotEmpty()
    }

    @Test
    fun `deserialize should return null when is an empty string`() {
        val json = "{\"replies\":\"\"}"
        val model = Json.decodeFromString<PostCommentRepliesStub>(json)

        assertThat(model.replies).isNull()
    }

    @Test
    fun `deserialize should return correct value`() {
        val json = postCommentRepliesJson
        val model = Json { ignoreUnknownKeys = true }.decodeFromString<PostCommentRepliesStub>(json)

        assertThat(
            model.replies?.data?.postCommentsResponse
        ).isNotEmpty()

        assertThat(
            model.replies?.data?.postCommentsResponse?.first()?.data?.body
        ).isEqualTo("Can you point out the issues in the article for those of us who don’t know coroutines and want to learn it right?")
    }
}