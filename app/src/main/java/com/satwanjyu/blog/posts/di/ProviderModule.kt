package com.satwanjyu.blog.posts.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.satwanjyu.blog.BuildConfig
import com.satwanjyu.blog.data.remote.PostApi
import com.satwanjyu.blog.posts.data.local.objectbox.MyObjectBox
import com.satwanjyu.blog.posts.data.local.objectbox.PostEntity
import com.satwanjyu.blog.posts.data.local.room.PostDao
import com.satwanjyu.blog.posts.data.local.room.PostDb
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
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

    @Singleton
    @Provides
    fun provideObjectBoxStore(@ApplicationContext context: Context): BoxStore {
        val store = MyObjectBox.builder()
            .androidContext(context)
            .build()

        // object browser
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(store).start(context)
            Log.i("ObjectBrowser", "Started: $started")
        }
        return store
    }

    @Singleton
    @Provides
    fun providePostBox(boxStore: BoxStore): Box<PostEntity> {
        return boxStore.boxFor(PostEntity::class.java)
    }
}