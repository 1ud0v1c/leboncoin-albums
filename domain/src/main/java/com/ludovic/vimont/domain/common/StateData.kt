package com.ludovic.vimont.domain.common

sealed class StateData<out R> {
    data class Success<out T>(val data: T) : StateData<T>()
    data class Error(val errorMessage: String) : StateData<Nothing>()
    object Loading : StateData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[errorMessage=$errorMessage]"
            Loading -> "Loading"
        }
    }
}