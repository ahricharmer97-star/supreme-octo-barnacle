package com.sd.laborator.merkle.business.interfaces

interface IMerkleService {
    fun buildFromZone(keys: List<String>)
    fun addLeaf(key: String)
    fun removeLeaf(key: String)
    fun contains(key: String): Boolean
    fun getRootHash(): String?
}
