package com.satwanjyu.blog.posts.data.local.objectbox

import com.satwanjyu.blog.posts.data.Post
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class PostEntity(
    @Id(assignable = true)
    var id: Long,
    var content: String
) {
    fun toPost() = Post(id.toString(), content)
}
