package com.ludovic.vimont.domain.usecases

interface UseCase<I, O> {
    fun execute(input: I): O
}