package com.deliverytz.di

import android.content.Context
import com.deliverytz.data.UserRepositoryImpl
import com.deliverytz.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext context: Context) : UserRepository {
        return UserRepositoryImpl(context)
    }

}