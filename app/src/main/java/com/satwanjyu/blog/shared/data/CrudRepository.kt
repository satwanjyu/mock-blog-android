package com.satwanjyu.blog.shared.data

import kotlinx.coroutines.flow.Flow

interface CrudRepository<T> {
    suspend fun create(data: T)
    fun read(): Flow<Data<List<T>>>
    suspend fun update(data: T)
    suspend fun delete(data: T)
}