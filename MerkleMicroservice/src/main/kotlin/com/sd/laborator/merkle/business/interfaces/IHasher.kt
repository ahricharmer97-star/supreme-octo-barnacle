package com.sd.laborator.merkle.business.interfaces

interface IHasher {
    fun hash(input: String): String
}
