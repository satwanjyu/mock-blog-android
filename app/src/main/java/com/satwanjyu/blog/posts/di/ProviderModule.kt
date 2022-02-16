package com.satwanjyu.blog.posts.di

import android.content.Context
import androidx.room.Room
import com.satwanjyu.blog.posts.data.local.room.PostDao
import com.satwanjyu.blog.shared.data.local.room.BlogDatabase
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
    fun providePostDatabase(@ApplicationContext context: Context): BlogDatabase {
        return Room.databaseBuilder(
            context,
            BlogDatabase::class.java,
            "post-database"
        ).build()
    }

    @Singleton
    @Provides
    fun providePostDao(blogDatabase: BlogDatabase): PostDao {
        return blogDatabase.postDao()
    }

}