package github.com.candalo.hashmobilechallenge.presentation.extensions

import android.content.Context
import github.com.candalo.hashmobilechallenge.presentation.formatter.InstantFormatter
import java.time.Instant

internal fun Instant.toElapsedDate(context: Context) = InstantFormatter(context).format(this)