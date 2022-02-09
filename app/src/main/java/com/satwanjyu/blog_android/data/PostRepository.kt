package com.satwanjyu.blog_android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostRepository @Inject constructor(
    retrofitPostRemoteDataSource: PostRemoteDataSource,
    roomPostLocalDataSource: PostLocalDataSource
) {

    val posts: Flow<List<Post>> = retrofitPostRemoteDataSource.remotePosts
        .onEach { roomPostLocalDataSource.setPosts(it) }
}

interface PostRemoteDataSource {
    val remotePosts: Flow<List<Post>>
}

interface PostLocalDataSource {
    val localPosts: Flow<List<Post>>
    suspend fun setPosts(posts: List<Post>)
}