package com.sd.laborator.presentation.clients

import com.sd.laborator.business.interfaces.ICacheClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.UriComponentsBuilder

@Component
class CacheClient : ICacheClient {

    @Value("\${cache.service.baseUrl}")
    private lateinit var _baseUrl: String

    private val _restTemplate = RestTemplate()

    override fun lookup(query: String): String? {
        return try {
            val uri = UriComponentsBuilder.fromHttpUrl("$_baseUrl/cache")
                .queryParam("query", "{query}")
                .buildAndExpand(query)
                .encode()
                .toUri()
            _restTemplate.getForObject(uri, String::class.java)
        } catch (e: HttpClientErrorException.NotFound) {
            null
        } catch (e: Exception) {
            println("CacheClient.lookup failed: ${e.message}")
            null
        }
    }

    override fun store(query: String, result: String) {
        try {
            val uri = UriComponentsBuilder.fromHttpUrl("$_baseUrl/cache")
                .queryParam("query", "{query}")
                .buildAndExpand(query)
                .encode()
                .toUri()
            val headers = HttpHeaders()
            headers.contentType = MediaType.TEXT_PLAIN
            _restTemplate.postForEntity(uri, HttpEntity(result, headers), Void::class.java)
        } catch (e: Exception) {
            println("CacheClient.store failed: ${e.message}")
        }
    }
}
