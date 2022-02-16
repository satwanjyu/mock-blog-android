package com.satwanjyu.blog.shared.data

sealed class Data<T> {
    abstract val data: T?

    class Live<T>(override val data: T?) : Data<T>()
    class Loading<T>(override val data: T? = null) : Data<T>()
    class Outdated<T>(override val data: T? = null, val message: String) : Data<T>()
}