package com.satwanjyu.blog.posts.data.local.room

import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.data.PostLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomPostLocalDataSource @Inject constructor(
    private val postDao: PostDao
) : PostLocalDataSource {
    override fun getPosts(): Flow<List<Post>> = postDao.getPosts().map { entities ->
        entities.map { entity ->
            entity.toPost()
        }
    }

    override suspend fun setPosts(posts: List<Post>) {
        val postEntities = posts.map { PostEntity(it.id.toInt(), it.content) }
        postDao.deletePostsFrom(postEntities.size)
        postDao.setPosts(postEntities)
    }
}