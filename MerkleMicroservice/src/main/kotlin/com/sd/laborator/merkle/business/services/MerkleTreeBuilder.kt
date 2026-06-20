package com.sd.laborator.merkle.business.services

import com.sd.laborator.merkle.business.interfaces.IHasher
import com.sd.laborator.merkle.business.interfaces.IMerkleTreeBuilder
import com.sd.laborator.merkle.business.models.MerkleNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class MerkleTreeBuilder : IMerkleTreeBuilder {

    @Autowired
    private lateinit var _hasher: IHasher

    override fun build(leafKeys: List<String>): MerkleNode? {
        if (leafKeys.isEmpty()) return null

        var level: MutableList<MerkleNode> = leafKeys
            .sorted()
            .map { key -> MerkleNode(hash = _hasher.hash(key), leafKey = key) }
            .toMutableList()

        while (level.size > 1) {
            val nextLevel = mutableListOf<MerkleNode>()
            var i = 0
            while (i < level.size) {
                val left = level[i]
                val right = if (i + 1 < level.size) level[i + 1] else level[i]
                val combinedHash = _hasher.hash(left.hash + right.hash)
                nextLevel.add(MerkleNode(hash = combinedHash, left = left, right = right))
                i += 2
            }
            level = nextLevel
        }

        return level[0]
    }
}
