package com.github.candalo.yarc.presentation.extensions

import android.content.Context
import com.github.candalo.yarc.presentation.formatter.InstantFormatter
import java.time.Instant

internal fun Instant.toElapsedDate(context: Context) = InstantFormatter(context).format(this)