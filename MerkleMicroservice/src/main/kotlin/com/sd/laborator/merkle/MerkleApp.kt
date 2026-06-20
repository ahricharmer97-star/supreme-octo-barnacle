package com.sd.laborator.merkle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MerkleApp

fun main(args: Array<String>) {
    runApplication<MerkleApp>(*args)
}
