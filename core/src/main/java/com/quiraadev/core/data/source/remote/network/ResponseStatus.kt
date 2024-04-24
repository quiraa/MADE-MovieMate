package com.quiraadev.core.data.source.remote.network

sealed class ResponseStatus<out R> {
	data class Success<out T>(val data: T): ResponseStatus<T>()
	data class Error(val errorMessage: String) : ResponseStatus<Nothing>()
	data object Empty : ResponseStatus<Nothing>()
}