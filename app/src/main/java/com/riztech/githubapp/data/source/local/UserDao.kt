package com.riztech.githubapp.data.source.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM userentity")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<UserEntity>)

    @Query("SELECT * FROM userentity WHERE " +
            "login LIKE :queryString " +
            "ORDER BY login ASC")
    fun reposByName(queryString: String): PagingSource<Int, UserEntity>

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM remote_keys")
    suspend fun clearUser()
}