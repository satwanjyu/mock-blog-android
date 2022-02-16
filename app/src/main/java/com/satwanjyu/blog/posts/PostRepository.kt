package com.satwanjyu.blog.posts

import com.satwanjyu.blog.posts.data.Data
import com.satwanjyu.blog.posts.data.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Data<List<Post>>>
    suspend fun updatePosts()
    suspend fun insertPost(content: String)
}