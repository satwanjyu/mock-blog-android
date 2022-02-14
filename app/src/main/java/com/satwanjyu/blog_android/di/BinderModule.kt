package com.satwanjyu.blog_android.di

import com.satwanjyu.blog_android.PostRepository
import com.satwanjyu.blog_android.data.PostLocalDataSource
import com.satwanjyu.blog_android.data.PostRemoteDataSource
import com.satwanjyu.blog_android.data.PostRepositoryImpl
import com.satwanjyu.blog_android.data.local.objectbox.ObjectBoxPostLocalDataSource
import com.satwanjyu.blog_android.data.remote.ktor.KtorPostRemoteDataSource
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