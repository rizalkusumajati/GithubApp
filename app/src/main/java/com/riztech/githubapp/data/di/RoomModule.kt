package com.riztech.githubapp.data.di

import android.app.Application
import androidx.room.Room
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.local.RemoteKeyDao
import com.riztech.githubapp.data.source.local.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun getRoom( application: Application) : GithubDatabase {
        return  Room.databaseBuilder(
            application ,
            GithubDatabase::class.java, "github.db"
        ).build()
    }

    @Provides
    @Singleton
    fun getUserDao(db: GithubDatabase): UserDao {
        return db.userDao()
    }
    @Provides
    @Singleton
    fun getRemoteKeyDao(db: GithubDatabase): RemoteKeyDao {
        return db.remoteKeyDao()
    }
}