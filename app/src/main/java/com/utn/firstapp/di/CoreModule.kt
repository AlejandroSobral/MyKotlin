package com.utn.firstapp.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.utn.firstapp.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideGsonInstance(): Gson = Gson()

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    @Provides
    fun providePreferencesManager(sharedPreferences: SharedPreferences, gson: Gson) = PreferencesManager(sharedPreferences, gson)
}