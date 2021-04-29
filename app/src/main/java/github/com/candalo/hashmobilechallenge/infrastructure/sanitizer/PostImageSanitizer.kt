package github.com.candalo.hashmobilechallenge.infrastructure.sanitizer

internal class PostImageSanitizer : Sanitizer<String> {
    override fun sanitize(data: String): String = data.replace("amp;", "")
}