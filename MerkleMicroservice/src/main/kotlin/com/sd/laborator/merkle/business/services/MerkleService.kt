package com.sd.laborator.merkle.business.services

import com.sd.laborator.merkle.business.interfaces.IMerkleService
import com.sd.laborator.merkle.business.interfaces.IMerkleTreeBuilder
import com.sd.laborator.merkle.business.models.MerkleNode
import com.sd.laborator.merkle.persistence.interfaces.ILeafRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MerkleService : IMerkleService {

    @Autowired
    private lateinit var _leafRepository: ILeafRepository

    @Autowired
    private lateinit var _treeBuilder: IMerkleTreeBuilder

    private var _root: MerkleNode? = null

    override fun buildFromZone(keys: List<String>) {
        _leafRepository.clear()
        _leafRepository.addAll(keys)
        rebuild()
    }

    override fun addLeaf(key: String) {
        _leafRepository.add(key)
        rebuild()
    }

    override fun removeLeaf(key: String) {
        _leafRepository.remove(key)
        rebuild()
    }

    override fun contains(key: String): Boolean {
        return _leafRepository.contains(key)
    }

    override fun getRootHash(): String? {
        return _root?.hash
    }

    private fun rebuild() {
        _root = _treeBuilder.build(_leafRepository.getAll())
    }
}
