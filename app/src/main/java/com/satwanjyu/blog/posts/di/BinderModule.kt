package com.satwanjyu.blog.posts.di

import com.satwanjyu.blog.posts.PostRepository
import com.satwanjyu.blog.posts.data.PostLocalDataSource
import com.satwanjyu.blog.posts.data.PostRemoteDataSource
import com.satwanjyu.blog.posts.data.PostRepositoryImpl
import com.satwanjyu.blog.posts.data.local.room.RoomPostLocalDataSource
import com.satwanjyu.blog.posts.data.remote.ktor.KtorPostRemoteDataSource
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
        postLocalDataSource: RoomPostLocalDataSource
    ): PostLocalDataSource

    @Singleton
    @Binds
    abstract fun bindPostRepository(
        postRepository: PostRepositoryImpl
    ): PostRepository
}