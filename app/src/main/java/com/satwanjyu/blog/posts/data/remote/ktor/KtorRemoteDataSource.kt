package com.satwanjyu.blog.posts.data.remote.ktor

import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.data.remote.PostDto
import com.satwanjyu.blog.shared.data.remote.RemoteDataSource
import com.satwanjyu.blog.shared.data.remote.Resource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KtorRemoteDataSource @Inject constructor(
    private val client: HttpClient
) : RemoteDataSource<Post> {

    override fun get(): Flow<Resource<List<Post>>> {
        return flow {
            while (true) {
                try {
                    val postDtos: List<PostDto> = client.get(
                        BASE_URL + "posts"
                    ).body()
                    val posts = postDtos.map { it.toPost() }
                    emit(Resource.Success(posts))
                } catch (e: Exception) {
                    emit(
                        Resource.Error(
                            message = e.message ?: "Unexpected error"
                        )
                    )
                }
                delay(5000L)
            }
        }
    }

    override suspend fun set(data: Post) {
        client.post(BASE_URL + "posts") {
            contentType(ContentType.Application.Json)
            setBody(PostDto(id = null, content = data.content))
        }
    }

    companion object {
        const val BASE_URL = "https://61db86294593510017aff8db.mockapi.io/"
    }
}