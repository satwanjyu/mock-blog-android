package com.satwanjyu.blog_android.data.local.room

import android.util.Log
import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.data.PostLocalDataSource
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
        Log.d("BlogRoomDataSource", "Database insert")
        val postEntities = posts.map { PostEntity(it.id.toInt(), it.content) }
        postDao.deletePostsFrom(postEntities.size)
        postDao.setPosts(postEntities)
    }
}