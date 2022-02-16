package com.satwanjyu.blog.shared.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource<T> {
    fun get(): Flow<List<T>>
    suspend fun set(data: List<T>)
}