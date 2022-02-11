package com.satwanjyu.blog_android.data.local.objectbox

import android.util.Log
import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.data.PostLocalDataSource
import io.objectbox.Box
import io.objectbox.kotlin.greater
import io.objectbox.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObjectBoxPostLocalDataSource @Inject constructor(
    private val postEntityBox: Box<PostEntity>
) : PostLocalDataSource {

    override fun getPosts(): Flow<List<Post>> {
        return postEntityBox.query().build().subscribe().toFlow().map { postEntities ->
            postEntities.map { postEntity ->
                postEntity.toPost()
            }
        }
    }

    override suspend fun setPosts(posts: List<Post>) {
        Log.d("BlogObjectBoxDataSource", "Box put")

        postEntityBox.query(PostEntity_.id greater posts.size)
            .build()
            .remove()

        val postEntities = posts.map {
            PostEntity(it.id.toLong(), it.content)
        }
        postEntityBox.put(postEntities)
    }

    override suspend fun insertPost(content: String) {
        postEntityBox.put(PostEntity(0, content))
    }
}