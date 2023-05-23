package com.utn.firstapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.utn.firstapp.fragments.LoginFrgmtViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object UserModule {


    @Provides
    fun provideMyLoginViewModel(@ApplicationContext context: Context): LoginFrgmtViewModel {
        return LoginFrgmtViewModel(context)
    }
}