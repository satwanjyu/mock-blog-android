package com.satwanjyu.blog_android.data.local.objectbox

import com.satwanjyu.blog_android.data.Post
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
