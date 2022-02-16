package com.satwanjyu.blog.shared.data

import com.satwanjyu.blog.shared.data.local.LocalDataSource
import com.satwanjyu.blog.shared.data.remote.RemoteDataSource

interface CachedLiveRepository<T> : CrudRepository<T> {
    val localDataSource: LocalDataSource<T>
    val remoteDataSource: RemoteDataSource<T>
    suspend fun cachePosts()
}