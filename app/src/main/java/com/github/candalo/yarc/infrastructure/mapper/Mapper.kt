package com.github.candalo.yarc.infrastructure.mapper

interface Mapper<I,O> {
    fun map(input: I): O
}