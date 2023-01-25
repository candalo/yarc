package com.github.candalo.yarc.infrastructure.sanitizer

internal interface Sanitizer<T> {
    fun sanitize(data: T): T
}