package com.satwanjyu.blog_android.data.remote

import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.data.PostRemoteDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import javax.inject.Inject

class RetrofitPostRemoteDataSource @Inject constructor(
    private val postApi: PostApi
) : PostRemoteDataSource {
    override fun getPosts(): Flow<List<Post>> = flow {
        while (true) {
            val remotePosts = postApi.getPosts().map { it.toPost() }
            emit(remotePosts)
            delay(5000L)
        }
    }

    override suspend fun postPost(content: String) {
        TODO("Not yet implemented")
    }
}

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    companion object {
        const val BASE_URL = "https://61db86294593510017aff8db.mockapi.io/"
    }
}