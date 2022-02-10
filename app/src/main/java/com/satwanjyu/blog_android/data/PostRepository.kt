package com.satwanjyu.blog_android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val retrofitPostRemoteDataSource: PostRemoteDataSource,
    private val roomPostLocalDataSource: PostLocalDataSource
) {

    val posts: Flow<List<Post>> = roomPostLocalDataSource.getPosts()

    suspend fun updatePosts() {
        retrofitPostRemoteDataSource.getPosts().collect { posts ->
            roomPostLocalDataSource.setPosts(posts)
        }
    }
}

interface PostRemoteDataSource {
    fun getPosts(): Flow<List<Post>>
}

interface PostLocalDataSource {
    fun getPosts(): Flow<List<Post>>
    suspend fun setPosts(posts: List<Post>)
}