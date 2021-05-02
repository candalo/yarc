package github.com.candalo.yarc.presentation.extensions

import android.content.Context
import github.com.candalo.yarc.presentation.formatter.InstantFormatter
import java.time.Instant

internal fun Instant.toElapsedDate(context: Context) = InstantFormatter(context).format(this)