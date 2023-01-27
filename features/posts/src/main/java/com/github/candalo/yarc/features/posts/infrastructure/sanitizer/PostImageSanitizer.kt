package com.github.candalo.yarc.features.posts.infrastructure.sanitizer

import com.github.candalo.yarc.converter.Sanitizer
import javax.inject.Inject

internal class PostImageSanitizer @Inject constructor(): Sanitizer<String> {
    override fun sanitize(data: String): String = data.replace("amp;", "")
}