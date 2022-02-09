package com.satwanjyu.blog_android.data

import com.satwanjyu.blog_android.data.remote.PostRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    postRemoteDataSource: PostRemoteDataSource
) {

    val posts: Flow<List<Post>> = postRemoteDataSource.remotePosts
}