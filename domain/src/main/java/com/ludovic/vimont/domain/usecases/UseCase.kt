package com.ludovic.vimont.domain.usecases

interface UseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}