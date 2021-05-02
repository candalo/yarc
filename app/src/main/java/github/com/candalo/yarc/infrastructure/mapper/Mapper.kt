package github.com.candalo.yarc.infrastructure.mapper

interface Mapper<I,O> {
    fun map(input: I): O
}