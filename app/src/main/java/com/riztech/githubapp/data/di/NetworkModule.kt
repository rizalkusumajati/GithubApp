package com.riztech.githubapp.data.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.riztech.githubapp.BuildConfig
import com.riztech.githubapp.data.model.mapper.RemoteLocalUserMapper
import com.riztech.githubapp.data.model.mapper.UserEntityMapper
import com.riztech.githubapp.data.repository.UserRepositoryImpl
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.remote.GithubApi
import com.riztech.githubapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.getCacheDir(), cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, interceptors: ArrayList<Interceptor>): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        interceptors.forEach {
            client.addInterceptor(it)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://api.github.com")
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideUserRepository(githubApi: GithubApi, userDatabase: GithubDatabase, mapper: RemoteLocalUserMapper, localMapper: UserEntityMapper): UserRepository = UserRepositoryImpl(githubApi, userDatabase, mapper, localMapper)
}