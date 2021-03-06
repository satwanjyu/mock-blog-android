package com.satwanjyu.blog.posts.data.remote

import com.satwanjyu.blog.posts.data.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String?,
    val content: String,
) {
    fun toPost() = Post(id!!, content)
}
