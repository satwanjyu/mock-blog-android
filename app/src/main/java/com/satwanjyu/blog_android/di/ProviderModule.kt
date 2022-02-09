package com.satwanjyu.blog_android.di

import android.content.Context
import androidx.room.Room
import com.satwanjyu.blog_android.data.local.PostDao
import com.satwanjyu.blog_android.data.local.PostDb
import com.satwanjyu.blog_android.data.remote.PostApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providePostApiService(
        moshi: Moshi
    ): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(PostApi.BASE_URL)
            .build()
            .create(PostApi::class.java)
    }

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