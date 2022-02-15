package com.satwanjyu.blog.mainscreen.data

import com.satwanjyu.blog.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val postLocalDataSource: PostLocalDataSource
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> = postLocalDataSource.getPosts()

    override suspend fun updatePosts() {
        postRemoteDataSource.getPosts().collect { posts ->
            postLocalDataSource.setPosts(posts)
        }
    }

    override suspend fun insertPost(content: String) {
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
}