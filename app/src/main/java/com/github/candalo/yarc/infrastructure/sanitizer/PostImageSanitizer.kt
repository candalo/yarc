package com.github.candalo.yarc.infrastructure.sanitizer

internal class PostImageSanitizer : Sanitizer<String> {
    override fun sanitize(data: String): String = data.replace("amp;", "")
}