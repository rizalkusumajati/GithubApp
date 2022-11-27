package com.riztech.githubapp.data.di

import com.riztech.githubapp.presentation.detail.DetailFragment
import com.riztech.githubapp.presentation.favorite.FavoriteFragment
import com.riztech.githubapp.presentation.home.HomeFragment
import com.riztech.githubapp.presentation.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributePopularFragment(): PopularFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}