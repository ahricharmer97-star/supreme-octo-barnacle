package com.sd.laborator.merkle.business.models

class MerkleNode(
    var hash: String,
    var left: MerkleNode? = null,
    var right: MerkleNode? = null,
    var leafKey: String? = null
) {
    fun isLeaf(): Boolean = left == null && right == null
}
