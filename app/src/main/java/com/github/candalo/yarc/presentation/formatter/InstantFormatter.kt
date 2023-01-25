package com.github.candalo.yarc.presentation.formatter

import android.content.Context
import com.github.candalo.yarc.R
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

private const val MINUTES = 60
private const val HOURS = 24

internal class InstantFormatter(private val context: Context) {
    fun format(instant: Instant): String {
        val duration = Duration.between(
            instant.atZone(ZoneId.systemDefault()),
            Instant.now().atZone(ZoneId.systemDefault())
        )

        return when {
            duration.toMinutes() < MINUTES -> {
                context.getString(
                    R.string.post_creation_date_minutes_interval,
                    duration.toMinutes().toString()
                )
            }
            duration.toHours() < HOURS -> {
                context.getString(
                    R.string.post_creation_date_hours_interval,
                    duration.toHours().toString()
                )
            }
            else -> {
                context.getString(
                    R.string.post_creation_date_days_interval,
                    duration.toDays().toString()
                )
            }
        }
    }
}