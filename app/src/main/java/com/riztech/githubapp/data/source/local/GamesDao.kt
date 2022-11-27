package com.riztech.githubapp.data.source.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface GamesDao {
    @Transaction
    @Query("SELECT * FROM gamesentity")
    suspend fun getAll(): List<GamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: GamesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GamesEntity>)

    @Query("SELECT * FROM gamesentity WHERE " +
            "name LIKE :queryString " +
            "ORDER BY name ASC")
    fun reposByName(queryString: String): PagingSource<Int, GamesEntity>

    @Query("SELECT * FROM gamesentity WHERE " +
            "id=:queryString")
    suspend fun getById(queryString: Int): GamesEntity?

    @Delete
    suspend fun delete(user: GamesEntity)

    @Query("DELETE FROM gamesentity")
    suspend fun clearUser()
}