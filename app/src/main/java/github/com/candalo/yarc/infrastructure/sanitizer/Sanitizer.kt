package github.com.candalo.yarc.infrastructure.sanitizer

internal interface Sanitizer<T> {
    fun sanitize(data: T): T
}