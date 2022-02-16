package com.satwanjyu.blog.posts.di

import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.data.PostRepository
import com.satwanjyu.blog.posts.data.local.room.RoomPostLocalDataSource
import com.satwanjyu.blog.posts.data.remote.ktor.KtorRemoteDataSource
import com.satwanjyu.blog.shared.data.CachedLiveRepository
import com.satwanjyu.blog.shared.data.local.LocalDataSource
import com.satwanjyu.blog.shared.data.remote.RemoteDataSource
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
        postRemoteDataSource: KtorRemoteDataSource
    ): RemoteDataSource<Post>

    @Singleton
    @Binds
    abstract fun bindPostLocalDataSource(
        postLocalDataSource: RoomPostLocalDataSource
    ): LocalDataSource<Post>

    @Singleton
    @Binds
    abstract fun bindPostRepository(
        postRepository: PostRepository
    ): CachedLiveRepository<Post>
}