package com.github.candalo.yarc.features.posts.infrastructure.formatter

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant

internal class InstantFormatterTest {
    private val formatter = InstantFormatter()

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