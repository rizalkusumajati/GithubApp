package com.riztech.githubapp.data.di

import android.app.Application
import com.riztech.githubapp.GithubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelFactoryModule::class,
        RoomModule::class,
        NetworkModule::class
    ]
)
interface AppComponent: AndroidInjector<GithubApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}