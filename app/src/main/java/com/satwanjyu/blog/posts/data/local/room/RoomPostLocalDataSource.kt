package com.satwanjyu.blog.posts.data.local.room

import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.shared.data.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomPostLocalDataSource @Inject constructor(
    private val postDao: PostDao
) : LocalDataSource<Post> {
    override fun get(): Flow<List<Post>> = postDao.getPosts().map { entities ->
        entities.map { entity ->
            entity.toPost()
        }
    }

    override suspend fun set(data: List<Post>) {
        val postEntities = data.map { PostEntity(it.id.toInt(), it.content) }
        postDao.deletePostsFrom(postEntities.size)
        postDao.setPosts(postEntities)
    }
}