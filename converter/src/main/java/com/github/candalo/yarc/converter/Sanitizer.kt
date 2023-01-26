package com.github.candalo.yarc.converter

interface Sanitizer<T> {
    fun sanitize(data: T): T
}