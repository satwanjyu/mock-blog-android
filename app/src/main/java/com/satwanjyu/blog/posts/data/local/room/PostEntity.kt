package com.satwanjyu.blog.posts.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satwanjyu.blog.posts.data.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val content: String
) {
    fun toPost() = Post(id.toString(), content)
}
