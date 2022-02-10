package com.satwanjyu.blog_android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PostRepository @Inject constructor(
    val retrofitPostRemoteDataSource: PostRemoteDataSource,
    val roomPostLocalDataSource: PostLocalDataSource
) {

    val posts: Flow<List<Post>> = roomPostLocalDataSource.localPosts

    suspend fun updatePosts() {
        retrofitPostRemoteDataSource.remotePosts.collect { posts ->
            roomPostLocalDataSource.setPosts(posts)
        }
    }
}

interface PostRemoteDataSource {
    val remotePosts: Flow<List<Post>>
}

interface PostLocalDataSource {
    val localPosts: Flow<List<Post>>
    suspend fun setPosts(posts: List<Post>)
}