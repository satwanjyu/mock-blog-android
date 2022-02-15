package com.satwanjyu.blog.mainscreen.data.remote

import com.satwanjyu.blog.mainscreen.data.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String?,
    val content: String,
) {
    fun toPost() = Post(id!!, content)
}
