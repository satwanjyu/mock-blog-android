package com.satwanjyu.blog_android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val postLocalDataSource: PostLocalDataSource
) {

    val posts: Flow<List<Post>> = postLocalDataSource.getPosts()

    suspend fun updatePosts() {
        postRemoteDataSource.getPosts().collect { posts ->
            postLocalDataSource.setPosts(posts)
        }
    }

    suspend fun insertPost(content: String) {
        postRemoteDataSource.postPost(content)
    }
}

interface PostRemoteDataSource {
    fun getPosts(): Flow<List<Post>>
    suspend fun postPost(content: String)
}

interface PostLocalDataSource {
    fun getPosts(): Flow<List<Post>>
    suspend fun setPosts(posts: List<Post>)
    suspend fun insertPost(content: String)
}