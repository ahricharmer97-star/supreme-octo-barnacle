package com.sd.laborator.merkle.persistence.repositories

import com.sd.laborator.merkle.persistence.interfaces.ILeafRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentSkipListSet

@Repository
class InMemoryLeafRepository : ILeafRepository {

    private val _keys = ConcurrentSkipListSet<String>()

    override fun add(key: String) {
        _keys.add(key)
    }

    override fun addAll(keys: List<String>) {
        _keys.addAll(keys)
    }

    override fun remove(key: String) {
        _keys.remove(key)
    }

    override fun contains(key: String): Boolean = _keys.contains(key)

    override fun getAll(): List<String> = _keys.toList()

    override fun clear() {
        _keys.clear()
    }
}
