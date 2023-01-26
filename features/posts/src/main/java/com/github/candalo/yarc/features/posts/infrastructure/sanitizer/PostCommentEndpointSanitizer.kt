package com.github.candalo.yarc.features.posts.infrastructure.sanitizer

import com.github.candalo.yarc.converter.Sanitizer

private const val COMMENT_LINK_FIRST_CHAR = "/"
private const val COMMENT_LINK_SUFFIX = ".json"

internal class PostCommentEndpointSanitizer : Sanitizer<String> {
    override fun sanitize(data: String): String {
        return data.replaceFirst(COMMENT_LINK_FIRST_CHAR, "")
            .dropLast(1)
            .plus(COMMENT_LINK_SUFFIX)
    }
}