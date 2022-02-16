package com.satwanjyu.blog.posts.data

import com.satwanjyu.blog.posts.PostRepository
import com.satwanjyu.blog.posts.data.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val postLocalDataSource: PostLocalDataSource
) : PostRepository {

    enum class DataState {
        LOADING, LIVE, OUTDATED
    }

    private var dataState = DataState.OUTDATED
    private var errorMessage = ""


    override fun getPosts(): Flow<Data<List<Post>>> = postLocalDataSource.getPosts().map { posts ->
        when (dataState) {
            DataState.LOADING -> Data.Loading(posts)
            DataState.LIVE -> Data.Live(posts)
            DataState.OUTDATED -> Data.Outdated(posts, errorMessage)
        }
    }

    override suspend fun updatePosts() {
        postRemoteDataSource.getPosts().collect { resource ->
            dataState = when (resource) {
                is Resource.Loading -> DataState.LOADING
                is Resource.Success -> {
                    updateLocalPosts(resource.data)
                    DataState.LIVE
                }
                is Resource.Error -> DataState.OUTDATED
            }
        }
    }

    private suspend fun updateLocalPosts(posts: List<Post>) {
        postLocalDataSource.setPosts(posts)
    }

    override suspend fun insertPost(content: String) {
        postRemoteDataSource.postPost(content)
    }
}

interface PostRemoteDataSource {
    fun getPosts(): Flow<Resource<List<Post>>>
    suspend fun postPost(content: String)
}

interface PostLocalDataSource {
    fun getPosts(): Flow<List<Post>>
    suspend fun setPosts(posts: List<Post>)
}