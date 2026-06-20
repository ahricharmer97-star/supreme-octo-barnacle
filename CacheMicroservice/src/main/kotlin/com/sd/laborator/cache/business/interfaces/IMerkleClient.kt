package com.sd.laborator.cache.business.interfaces

interface IMerkleClient {
    fun indexQuery(query: String)
    fun removeQuery(query: String)
    fun containsQuery(query: String): Boolean
}
