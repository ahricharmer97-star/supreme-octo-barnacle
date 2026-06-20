package com.sd.laborator.merkle.business.interfaces

import com.sd.laborator.merkle.business.models.MerkleNode

interface IMerkleTreeBuilder {
    fun build(leafKeys: List<String>): MerkleNode?
}
