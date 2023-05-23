package com.utn.firstapp.di

import android.content.Context
import com.utn.firstapp.UserDdetailFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object UserDetailModule {

    @Provides
    fun provideMyUserViewModel(@ApplicationContext context: Context): UserDdetailFragment {
        return UserDdetailFragment(context)
    }
}