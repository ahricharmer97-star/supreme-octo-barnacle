package com.sd.laborator.cache.business.models

class CacheEntry(private var id: Int, private var query: String, private var result: String, private var timestamp: Long) {

    var entryId: Int
        get() = id
        set(value) { id = value }

    var entryQuery: String
        get() = query
        set(value) { query = value }

    var entryResult: String
        get() = result
        set(value) { result = value }

    var entryTimestamp: Long
        get() = timestamp
        set(value) { timestamp = value }

    fun isFresh(ttlSeconds: Long): Boolean {
        val nowSeconds = System.currentTimeMillis() / 1000
        return (nowSeconds - timestamp) < ttlSeconds
    }

    override fun toString(): String {
        return "CacheEntry [id=$entryId, query=$entryQuery, result=$entryResult, timestamp=$entryTimestamp]"
    }
}
