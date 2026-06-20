package com.sd.laborator.cache.persistence.mappers

import com.sd.laborator.cache.business.models.CacheEntry
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class CacheEntryRowMapper : RowMapper<CacheEntry?> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): CacheEntry {
        return CacheEntry(
            rs.getInt("id"),
            rs.getString("query"),
            rs.getString("result"),
            rs.getLong("timestamp")
        )
    }
}
