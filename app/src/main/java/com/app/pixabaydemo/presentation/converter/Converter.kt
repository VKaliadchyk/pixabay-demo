package com.app.pixabaydemo.presentation.converter

interface Converter<S, R> {

    fun convert(source: S): R
}
