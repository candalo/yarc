package github.com.candalo.yarc.infrastructure.serializer

import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.time.Instant

@Serializable
private data class InstantStub(
    @Serializable(InstantSerializer::class)
    @SerialName("created_utc")
    val instant: Instant
)

internal class InstantSerializerTest {
    @Test
    fun `serialize should return correct value`() {
        val model = InstantStub(Instant.ofEpochSecond(1619481938))
        val json = Json.encodeToString(model)

        assertThat(json).isEqualTo("{\"created_utc\":1.619481938E12}")
    }

    @Test
    fun `deserialize should return correct value`() {
        val json = "{\"created_utc\":1619481938}"
        val model = Json.decodeFromString<InstantStub>(json)

        assertThat(model.instant.epochSecond).isEqualTo(1619481938)
    }
}