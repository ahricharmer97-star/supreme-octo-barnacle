package com.sd.laborator.business.interfaces

interface ICacheClient {
    fun lookup(query: String): String?
    fun store(query: String, result: String)
}
