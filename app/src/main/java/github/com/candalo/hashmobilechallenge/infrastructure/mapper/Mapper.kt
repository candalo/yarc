package github.com.candalo.hashmobilechallenge.infrastructure.mapper

interface Mapper<I,O> {
    fun map(input: I): O
}