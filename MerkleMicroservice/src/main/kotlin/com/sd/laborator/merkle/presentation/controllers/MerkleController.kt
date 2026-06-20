package com.sd.laborator.merkle.presentation.controllers

import com.sd.laborator.merkle.business.interfaces.IMerkleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tree")
open class MerkleController {

    @Autowired
    private lateinit var _merkleService: IMerkleService

    @PostMapping("/build")
    fun build(@RequestBody keys: List<String>) {
        _merkleService.buildFromZone(keys)
    }

    @PostMapping("/leaf")
    fun addLeaf(@RequestParam(required = true) key: String) {
        _merkleService.addLeaf(key)
    }

    @DeleteMapping("/leaf")
    fun removeLeaf(@RequestParam(required = true) key: String) {
        _merkleService.removeLeaf(key)
    }

    @GetMapping("/search")
    fun search(@RequestParam(required = true) key: String): Boolean {
        return _merkleService.contains(key)
    }

    @GetMapping("/root")
    fun root(): ResponseEntity<String> {
        val hash = _merkleService.getRootHash()
        return if (hash != null) ResponseEntity.ok(hash) else ResponseEntity.notFound().build()
    }
}
