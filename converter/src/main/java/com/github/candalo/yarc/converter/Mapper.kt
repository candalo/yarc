package com.github.candalo.yarc.converter

interface Mapper<I, O> {
    fun map(input: I): O
}