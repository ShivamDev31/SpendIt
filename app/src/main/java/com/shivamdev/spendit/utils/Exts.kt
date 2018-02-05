package com.shivamdev.spendit.utils

import com.shivamdev.spendit.data.models.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * Created by shivam on 01/02/18.
 */

inline fun <reified T : Any> Moshi.toJson(data: T?): String {
    val adapter: JsonAdapter<T> = adapter(T::class.java)
    return adapter.toJson(data)
}

inline fun <reified T : Any> Moshi.fromJson(json: String): T {
    val adapter: JsonAdapter<T> = adapter(T::class.java)
    return adapter.fromJson(json)!!
}

fun MutableList<User>.filterAndRemoveUser(userId: String): MutableList<User> {
    val newList = mutableListOf<User>()
    this.forEach {
        if (it.userId != userId) {
            newList.add(it)
        }
    }
    this.clear()
    this.addAll(newList)
    return this
}