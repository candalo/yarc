package github.com.candalo.yarc.presentation.formatter

import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.Duration
import java.time.Instant

internal class InstantFormatterTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val formatter = InstantFormatter(context)

    @Test
    fun formatShouldReturnCorrectElapsedTimeWhenDurationIsLessThanSixtyMinutes() {
        val instant = Instant.now().minus(Duration.ofMinutes(59))

        val elapsedTime = formatter.format(instant)

        assertThat(elapsedTime).isEqualTo("59m")
    }

    @Test
    fun formatShouldReturnCorrectElapsedTimeWhenDurationIsLessThanTwentyFourHours() {
        val instant = Instant.now().minus(Duration.ofHours(23))

        val elapsedTime = formatter.format(instant)

        assertThat(elapsedTime).isEqualTo("23h")
    }

    @Test
    fun formatShouldReturnCorrectElapsedTimeWhenDurationIsGreaterOrEqualThanTwentyFourHours() {
        val instant = Instant.now().minus(Duration.ofHours(24))

        val elapsedTime = formatter.format(instant)

        assertThat(elapsedTime).isEqualTo("1d")
    }
}