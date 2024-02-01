package com.app.pixabaydemo.domain.entity

sealed class Resource<T> {

    class Success<T>(val data: T) : Resource<T>()

    class Failure<T>(val throwable: Throwable, val data: T? = null) : Resource<T>()
}
