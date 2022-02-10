package com.satwanjyu.blog_android.data.remote.ktor

import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.data.PostRemoteDataSource
import com.satwanjyu.blog_android.data.remote.PostDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KtorPostRemoteDataSource @Inject constructor(
    private val client: HttpClient
) : PostRemoteDataSource {

    override fun getPosts(): Flow<List<Post>> {
        return flow {
            while (true) {
                val postDtos: List<PostDto> = client.get(
                    BASE_URL + "posts"
                ).body()
                val posts = postDtos.map { it.toPost() }
                emit(posts)
                delay(5000L)
            }
        }
    }

    companion object {
        const val BASE_URL = "https://61db86294593510017aff8db.mockapi.io/"
    }
}