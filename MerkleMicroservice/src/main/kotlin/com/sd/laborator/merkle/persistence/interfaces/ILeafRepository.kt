package com.sd.laborator.merkle.persistence.interfaces

interface ILeafRepository {
    fun add(key: String)
    fun addAll(keys: List<String>)
    fun remove(key: String)
    fun contains(key: String): Boolean
    fun getAll(): List<String>
    fun clear()
}
