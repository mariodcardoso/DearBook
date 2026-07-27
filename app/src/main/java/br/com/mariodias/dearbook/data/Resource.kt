package br.com.mariodias.dearbook.data

sealed interface Resource<out T> {
    data class Success<T>(val response: T) : Resource<T>
    data class Error<T>(val exception: Throwable) : Resource<T>
}