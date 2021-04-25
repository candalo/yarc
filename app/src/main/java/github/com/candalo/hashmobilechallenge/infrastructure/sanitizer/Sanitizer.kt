package github.com.candalo.hashmobilechallenge.infrastructure.sanitizer

internal interface Sanitizer<T> {
    fun sanitize(data: T): T
}