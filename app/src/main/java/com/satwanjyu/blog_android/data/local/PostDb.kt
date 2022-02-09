package com.satwanjyu.blog_android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDb : RoomDatabase() {
    abstract fun postDao(): PostDao
}