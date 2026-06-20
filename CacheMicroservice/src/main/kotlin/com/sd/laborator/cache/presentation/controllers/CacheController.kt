package com.sd.laborator.cache.presentation.controllers

import com.sd.laborator.cache.business.interfaces.ICacheService
import com.sd.laborator.cache.persistence.interfaces.ICacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RestController
class CacheController {

    @Autowired
    private lateinit var _cacheService: ICacheService

    @Autowired
    private lateinit var _cacheRepository: ICacheRepository

    @PostConstruct
    fun init() {
        _cacheRepository.createTable()
    }

    @GetMapping("/cache")
    fun lookup(@RequestParam(required = true) query: String): ResponseEntity<String> {
        val result = _cacheService.lookup(query)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @PostMapping("/cache")
    fun store(@RequestParam(required = true) query: String, @RequestBody result: String) {
        _cacheService.store(query, result)
    }
}
