package com.satwanjyu.blog.posts.di

import android.content.Context
import androidx.room.Room
import com.satwanjyu.blog.posts.data.local.room.PostDao
import com.satwanjyu.blog.posts.data.local.room.PostDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Singleton
    @Provides
    fun providePostDatabase(@ApplicationContext context: Context): PostDb {
        return Room.databaseBuilder(
            context,
            PostDb::class.java,
            "post-database"
        ).build()
    }

    @Singleton
    @Provides
    fun providePostDao(postDb: PostDb): PostDao {
        return postDb.postDao()
    }

}