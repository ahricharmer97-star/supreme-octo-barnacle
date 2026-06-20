package com.sd.laborator.merkle.business.services

import com.sd.laborator.merkle.business.interfaces.IHasher
import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
class Sha256Hasher : IHasher {
    override fun hash(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
