package com.github.candalo.yarc.features.posts.infrastructure.sanitizer

import com.github.candalo.yarc.converter.Sanitizer

internal class PostImageSanitizer : Sanitizer<String> {
    override fun sanitize(data: String): String = data.replace("amp;", "")
}