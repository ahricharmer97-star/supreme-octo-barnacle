package com.sd.laborator.cache.business.services

import com.sd.laborator.cache.business.interfaces.ICacheService
import com.sd.laborator.cache.business.interfaces.IMerkleClient
import com.sd.laborator.cache.business.models.CacheEntry
import com.sd.laborator.cache.persistence.interfaces.ICacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CacheService : ICacheService {

    @Autowired
    private lateinit var _cacheRepository: ICacheRepository

    @Autowired
    private lateinit var _merkleClient: IMerkleClient

    @Value("\${cache.ttl.seconds}")
    private var _ttlSeconds: Long = 3600

    override fun lookup(query: String): String? {
        if (!_merkleClient.containsQuery(query)) {
            return null
        }
        val entry = _cacheRepository.getByQuery(query) ?: return null
        if (!entry.isFresh(_ttlSeconds)) {
            return null
        }
        return entry.entryResult
    }

    override fun store(query: String, result: String) {
        val now = System.currentTimeMillis() / 1000
        val existing = _cacheRepository.getByQuery(query)
        if (existing != null) {
            existing.entryResult = result
            existing.entryTimestamp = now
            _cacheRepository.update(existing)
        } else {
            _cacheRepository.add(CacheEntry(-1, query, result, now))
        }
        _merkleClient.indexQuery(query)
    }
}
