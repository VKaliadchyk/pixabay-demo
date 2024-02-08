package com.app.pixabaydemo.data.cache

interface DataCache<K, V> {

    fun get(key: K): V?

    fun put(key: K, data: V)

    fun contains(key: K): Boolean

    fun remove(key: K)

    fun clearCache()
}
