package com.satwanjyu.blog.shared.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satwanjyu.blog.messages.data.local.room.MessageDao
import com.satwanjyu.blog.messages.data.local.room.MessageEntity
import com.satwanjyu.blog.posts.data.local.room.PostDao
import com.satwanjyu.blog.posts.data.local.room.PostEntity

@Database(entities = [PostEntity::class, MessageEntity::class], version = 1)
abstract class BlogDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun messageDao(): MessageDao
}