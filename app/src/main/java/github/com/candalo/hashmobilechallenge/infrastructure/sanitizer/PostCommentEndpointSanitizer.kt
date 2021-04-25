package github.com.candalo.hashmobilechallenge.infrastructure.sanitizer

private const val COMMENT_LINK_STRUCTURE_REGEX = "\\/(r)\\/(\\w+\\/){4}"
private const val COMMENT_LINK_FIRST_CHAR = "/"
private const val COMMENT_LINK_SUFFIX = ".json"

internal class PostCommentEndpointSanitizer : Sanitizer<String> {
    override fun sanitize(data: String): String {
        if (data.contains(COMMENT_LINK_STRUCTURE_REGEX)) {
            return data.replaceFirst(COMMENT_LINK_FIRST_CHAR, "")
                .dropLast(1)
                .plus(COMMENT_LINK_SUFFIX)
        }
        throw IllegalArgumentException("Invalid data format")
    }
}