package com.app.pixabaydemo

import com.app.pixabaydemo.data.cache.LocalDataCache
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalDataCacheTest {

    companion object {
        private const val FAKE_KEY = "FAKE_KEY"
        private const val FAKE_VALUE = "FAKE_VALUE"
    }

    private lateinit var dataCache: LocalDataCache<String, String>


    @Before
    fun setup() {
        dataCache = LocalDataCache(1_000_000)
    }

    @Test
    fun `get - should return cached data`() {
        //GIVEN
        dataCache.put(FAKE_KEY, FAKE_VALUE)

        //WHEN
        val result = dataCache.get(FAKE_KEY)

        //THEN
        assertEquals(FAKE_VALUE, result)
    }

    @Test
    fun `contains - should return true if it has cached data`() {
        //GIVEN
        dataCache.put(FAKE_KEY, FAKE_VALUE)

        //WHEN
        val result = dataCache.contains(FAKE_KEY)

        //THEN
        assertEquals(true, result)
    }

    @Test
    fun `clearCache - should delete cached data`() {
        //GIVEN
        dataCache.put(FAKE_KEY, FAKE_VALUE)

        //WHEN
        dataCache.clearCache()
        val result = dataCache.get(FAKE_KEY)

        //THEN
        assertEquals(null, result)
    }

    @Test
    fun `remove - should remove cached data by key`() {
        //GIVEN
        dataCache.put(FAKE_KEY, FAKE_VALUE)

        //WHEN
        dataCache.remove(FAKE_KEY)
        val result = dataCache.get(FAKE_KEY)

        //THEN
        assertEquals(null, result)
    }

    @Test
    fun `get - should return null if cache lifetime is expired`() = runBlocking {
        //GIVEN
        val localDataCache = LocalDataCache<String, String>(0)
        localDataCache.put(FAKE_KEY, FAKE_VALUE)
        delay(10)

        //WHEN
        val result = localDataCache.get(FAKE_KEY)

        //THEN
        assertEquals(null, result)
    }

    @Test
    fun `contains - should return null if cache lifetime is expired`() = runBlocking {
        //GIVEN
        val localDataCache = LocalDataCache<String, String>(0)
        localDataCache.put(FAKE_KEY, FAKE_VALUE)
        delay(10)

        //WHEN
        val result = localDataCache.contains(FAKE_KEY)

        //THEN
        assertEquals(false, result)
    }
}
