package com.satwanjyu.blog_android.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satwanjyu.blog_android.data.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val content: String
) {
    fun toPost() = Post(id, content)
}
