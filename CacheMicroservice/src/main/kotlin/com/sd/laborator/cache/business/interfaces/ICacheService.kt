package com.sd.laborator.cache.business.interfaces

interface ICacheService {
    fun lookup(query: String): String?
    fun store(query: String, result: String)
}
