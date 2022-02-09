package com.satwanjyu.blog_android.data

import com.satwanjyu.blog_android.data.remote.RetrofitPostRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    retrofitPostRemoteDataSource: RetrofitPostRemoteDataSource
) {

    val posts: Flow<List<Post>> = retrofitPostRemoteDataSource.remotePosts
}

interface PostRemoteDataSource {
    val remotePosts: Flow<List<Post>>
}

interface PostLocalDataSource {
    val localPosts: Flow<List<Post>>
}