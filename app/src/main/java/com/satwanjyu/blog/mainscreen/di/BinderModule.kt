package com.satwanjyu.blog.mainscreen.di

import com.satwanjyu.blog.PostRepository
import com.satwanjyu.blog.mainscreen.data.PostLocalDataSource
import com.satwanjyu.blog.mainscreen.data.PostRemoteDataSource
import com.satwanjyu.blog.mainscreen.data.PostRepositoryImpl
import com.satwanjyu.blog.mainscreen.data.local.objectbox.ObjectBoxPostLocalDataSource
import com.satwanjyu.blog.mainscreen.data.remote.ktor.KtorPostRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Singleton
    @Binds
    abstract fun bindPostRemoteDataSource(
        postRemoteDataSource: KtorPostRemoteDataSource
    ): PostRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindPostLocalDataSource(
        postLocalDataSource: ObjectBoxPostLocalDataSource
    ): PostLocalDataSource

    @Singleton
    @Binds
    abstract fun bindPostRepository(
        postRepository: PostRepositoryImpl
    ): PostRepository
}