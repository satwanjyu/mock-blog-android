package com.satwanjyu.blog_android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setPosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts WHERE id > :size")
    suspend fun deletePostsFrom(size: Int)
}