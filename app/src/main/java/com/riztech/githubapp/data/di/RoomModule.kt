package com.riztech.githubapp.data.di

import android.app.Application
import androidx.room.Room
import com.riztech.githubapp.data.source.local.GamesDao
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.local.RemoteKeyDao
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
    fun getRemoteKeyDao(db: GithubDatabase): RemoteKeyDao {
        return db.remoteKeyDao()
    }

    @Provides
    @Singleton
    fun getGamesDao(db: GithubDatabase): GamesDao {
        return db.gamesDao()
    }
}