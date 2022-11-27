package com.riztech.githubapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, RemoteKeyEntity::class], version = 1)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}