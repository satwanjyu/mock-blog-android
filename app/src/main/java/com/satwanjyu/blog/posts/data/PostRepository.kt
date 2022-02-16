package com.satwanjyu.blog.posts.data

import com.satwanjyu.blog.shared.data.CachedLiveRepository
import com.satwanjyu.blog.shared.data.Data
import com.satwanjyu.blog.shared.data.local.LocalDataSource
import com.satwanjyu.blog.shared.data.remote.RemoteDataSource
import com.satwanjyu.blog.shared.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepository @Inject constructor(
    override val remoteDataSource: RemoteDataSource<Post>,
    override val localDataSource: LocalDataSource<Post>
) : CachedLiveRepository<Post> {

    enum class DataState {
        LOADING, LIVE, OUTDATED
    }

    private var dataState = DataState.OUTDATED
    private var errorMessage = "Repo error"

    override suspend fun create(data: Post) {
        remoteDataSource.set(data)
    }

    override fun read(): Flow<Data<List<Post>>> = localDataSource.get().map { posts ->
        when (dataState) {
            DataState.LOADING -> Data.Loading(posts)
            DataState.LIVE -> Data.Live(posts)
            DataState.OUTDATED -> Data.Outdated(posts, errorMessage)
        }
    }

    override suspend fun update(data: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(data: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun cachePosts() {
        remoteDataSource.get().collect { resource ->
            dataState = when (resource) {
                is Resource.Loading -> DataState.LOADING
                is Resource.Success -> {
                    localDataSource.set(resource.data)
                    DataState.LIVE
                }
                is Resource.Error -> {
                    errorMessage = resource.message
                    DataState.OUTDATED
                }
            }
        }
    }

}