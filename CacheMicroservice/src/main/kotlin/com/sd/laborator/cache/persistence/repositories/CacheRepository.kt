package com.sd.laborator.cache.persistence.repositories

import com.sd.laborator.cache.business.models.CacheEntry
import com.sd.laborator.cache.persistence.interfaces.ICacheRepository
import com.sd.laborator.cache.persistence.mappers.CacheEntryRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CacheRepository : ICacheRepository {

    @Autowired
    private lateinit var _jdbcTemplate: JdbcTemplate

    private var _rowMapper: RowMapper<CacheEntry?> = CacheEntryRowMapper()

    override fun createTable() {
        _jdbcTemplate.execute(
            """CREATE TABLE IF NOT EXISTS cache_entries(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                query VARCHAR(500) UNIQUE,
                result TEXT,
                timestamp INTEGER)"""
        )
    }

    override fun add(entry: CacheEntry) {
        try {
            _jdbcTemplate.update(
                "INSERT INTO cache_entries(query, result, timestamp) VALUES (?, ?, ?)",
                entry.entryQuery, entry.entryResult, entry.entryTimestamp
            )
        } catch (e: UncategorizedSQLException) {
            println("An error has occurred in ${this.javaClass.name}.add")
        }
    }

    override fun getByQuery(query: String): CacheEntry? {
        return try {
            _jdbcTemplate.queryForObject(
                "SELECT * FROM cache_entries WHERE query = ?", arrayOf(query), _rowMapper
            )
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun getAll(): MutableList<CacheEntry?> {
        return _jdbcTemplate.query("SELECT * FROM cache_entries", _rowMapper)
    }

    override fun update(entry: CacheEntry) {
        try {
            _jdbcTemplate.update(
                "UPDATE cache_entries SET result = ?, timestamp = ? WHERE query = ?",
                entry.entryResult, entry.entryTimestamp, entry.entryQuery
            )
        } catch (e: UncategorizedSQLException) {
            println("An error has occurred in ${this.javaClass.name}.update")
        }
    }

    override fun delete(query: String) {
        try {
            _jdbcTemplate.update("DELETE FROM cache_entries WHERE query = ?", query)
        } catch (e: UncategorizedSQLException) {
            println("An error has occurred in ${this.javaClass.name}.delete")
        }
    }
}
