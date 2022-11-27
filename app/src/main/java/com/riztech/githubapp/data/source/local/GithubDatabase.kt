package com.riztech.githubapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RemoteKeyEntity::class, GamesEntity::class], version = 1)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun gamesDao(): GamesDao
}