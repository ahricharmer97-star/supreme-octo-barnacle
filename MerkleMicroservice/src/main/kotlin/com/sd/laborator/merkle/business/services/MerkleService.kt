package com.sd.laborator.merkle.business.services

import com.sd.laborator.merkle.business.interfaces.IMerkleService
import com.sd.laborator.merkle.business.interfaces.IMerkleTreeBuilder
import com.sd.laborator.merkle.business.models.MerkleNode
import com.sd.laborator.merkle.persistence.interfaces.ILeafRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class MerkleService : IMerkleService {

    private val _log = LoggerFactory.getLogger(MerkleService::class.java)

    @Autowired
    private lateinit var _leafRepository: ILeafRepository

    @Autowired
    private lateinit var _treeBuilder: IMerkleTreeBuilder

    private var _root: MerkleNode? = null

    override fun buildFromZone(keys: List<String>) {
        _log.info("buildFromZone: {} keys", keys.size)
        _leafRepository.clear()
        _leafRepository.addAll(keys)
        rebuild()
    }

    override fun addLeaf(key: String) {
        _log.info("addLeaf key='{}'", key)
        _leafRepository.add(key)
        rebuild()
    }

    override fun removeLeaf(key: String) {
        _log.info("removeLeaf key='{}'", key)
        _leafRepository.remove(key)
        rebuild()
    }

    override fun contains(key: String): Boolean {
        val result = _leafRepository.contains(key)
        _log.info("contains key='{}' -> {}", key, result)
        return result
    }

    override fun getRootHash(): String? {
        return _root?.hash
    }

    private fun rebuild() {
        _root = _treeBuilder.build(_leafRepository.getAll())
        _log.info("rebuild done, leaves={}, root={}", _leafRepository.getAll().size, _root?.hash)
    }
}
