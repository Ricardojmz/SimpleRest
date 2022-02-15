package com.example.simplerest.di

import com.example.simplerest.repository.UserRepository
import com.example.simplerest.repository.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun userRepository(repo:UserRepositoryImp) : UserRepository
}