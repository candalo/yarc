package com.github.candalo.yarc.features.posts.infrastructure.formatter

import java.time.Duration
import java.time.Instant
import java.time.ZoneId

private const val MINUTES = 60
private const val HOURS = 24

internal class InstantFormatter {
    fun format(instant: Instant): String {
        val duration = Duration.between(
            instant.atZone(ZoneId.systemDefault()),
            Instant.now().atZone(ZoneId.systemDefault())
        )

        return when {
            duration.toMinutes() < MINUTES -> "${duration.toMinutes()}m"
            duration.toHours() < HOURS -> "${duration.toHours()}h"
            else -> "${duration.toDays()}d"
        }
    }
}

internal fun Instant.toElapsedDate() = InstantFormatter().format(this)
