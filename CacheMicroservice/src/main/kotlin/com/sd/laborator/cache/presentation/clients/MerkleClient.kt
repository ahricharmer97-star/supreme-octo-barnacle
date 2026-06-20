package com.sd.laborator.cache.presentation.clients

import com.sd.laborator.cache.business.interfaces.IMerkleClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.HttpClientErrorException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpEntity
import org.springframework.web.util.UriComponentsBuilder

@Component
open class MerkleClient : IMerkleClient {

    @Value("\${merkle.service.baseUrl}")
    private lateinit var _baseUrl: String

    private val _restTemplate = RestTemplate()

    private fun leafUri(key: String) = UriComponentsBuilder.fromHttpUrl("$_baseUrl/tree/leaf")
        .queryParam("key", "{key}")
        .buildAndExpand(key)
        .encode()
        .toUri()

    private fun searchUri(key: String) = UriComponentsBuilder.fromHttpUrl("$_baseUrl/tree/search")
        .queryParam("key", "{key}")
        .buildAndExpand(key)
        .encode()
        .toUri()

    override fun indexQuery(query: String) {
        try {
            _restTemplate.postForEntity(leafUri(query), HttpEntity.EMPTY, String::class.java)
        } catch (e: Exception) {
            println("MerkleClient.indexQuery failed: ${e.message}")
        }
    }

    override fun removeQuery(query: String) {
        try {
            _restTemplate.exchange(leafUri(query), HttpMethod.DELETE, HttpEntity.EMPTY, String::class.java)
        } catch (e: Exception) {
            println("MerkleClient.removeQuery failed: ${e.message}")
        }
    }

    override fun containsQuery(query: String): Boolean {
        return try {
            val response = _restTemplate.getForObject(searchUri(query), Boolean::class.java)
            response ?: false
        } catch (e: HttpClientErrorException.NotFound) {
            false
        } catch (e: Exception) {
            println("MerkleClient.containsQuery failed: ${e.message}")
            false
        }
    }
}
