package com.ludovic.vimont.domain.common

data class StateData<T>(val status: DataStatus,
                        val data: T?,
                        val errorMessage: String) {
    companion object {
        fun <T> loading(): StateData<T> {
            return StateData(DataStatus.LOADING, null, "")
        }

        fun <T> success(data: T?): StateData<T> {
            return StateData(DataStatus.SUCCESS, data, "")
        }

        fun <T> error(errorMessage: String): StateData<T> {
            return StateData(DataStatus.ERROR, null, errorMessage)
        }
    }
}