package com.android.androidtesttawkto.util

import java.lang.Exception

sealed class DataState<out S> {

    data class Success<out T>(val data: T): DataState<T>()

    data class LoadMore<out T>(val data: T): DataState<T>()

    data class Error(val exception: Exception): DataState<Nothing>()

    object Loading: DataState<Nothing>()

}