package com.satwanjyu.blog_android.data.remote

import com.satwanjyu.blog_android.data.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String,
    val content: String,
) {
    fun toPost() = Post(id, content)
}
