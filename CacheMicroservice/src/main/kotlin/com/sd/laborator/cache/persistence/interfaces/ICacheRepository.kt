package com.sd.laborator.cache.persistence.interfaces

import com.sd.laborator.cache.business.models.CacheEntry

interface ICacheRepository {
    fun createTable()
    fun add(entry: CacheEntry)
    fun getByQuery(query: String): CacheEntry?
    fun getAll(): MutableList<CacheEntry?>
    fun update(entry: CacheEntry)
    fun delete(query: String)
}
