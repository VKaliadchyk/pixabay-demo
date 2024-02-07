package com.app.pixabaydemo.domain.converter

interface Converter<S, R> {

    fun convert(source: S): R
}
