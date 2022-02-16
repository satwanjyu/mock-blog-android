package com.satwanjyu.blog.shared.data.remote

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<T> {
    fun get(): Flow<Resource<List<T>>>
    suspend fun set(data: T)
}