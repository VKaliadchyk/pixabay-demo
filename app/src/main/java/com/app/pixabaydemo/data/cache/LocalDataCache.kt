package com.app.pixabaydemo.data.cache

import java.lang.ref.WeakReference
import java.util.Collections


class LocalDataCache<K, V>(private val cacheLifetime: Long = DEFAULT_CACHE_LIFETIME_MILLISECONDS) : DataCache<K, V> {

    private val cache: MutableMap<K, Pair<WeakReference<V>?, Long>> =
        Collections.synchronizedMap(mutableMapOf())

    override fun get(key: K): V? {
        val cachedValue = cache[key]?.first?.get() ?: return null

        if (!isExpired(key)) {
            return cachedValue
        } else {
            cache.remove(key)
            return null
        }
    } 

    override fun put(key: K, data: V) {
        val currentTime = System.currentTimeMillis()
        cache[key] = Pair(WeakReference(data), currentTime)
    }

    override fun contains(key: K): Boolean {
        return cache.containsKey(key)
    }

    override fun clearCache() {
        cache.clear()
    }

    override fun remove(key: K) {
        cache.remove(key)
    }

    private fun isExpired(key: K): Boolean {
        val timestamp = cache[key]?.second ?: return false
        val currentTime = System.currentTimeMillis()
        return currentTime - timestamp > cacheLifetime
    }

    companion object {
        private const val DEFAULT_CACHE_LIFETIME_MILLISECONDS = 60_000L
    }
}
