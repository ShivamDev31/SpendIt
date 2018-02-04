package com.shivamdev.spendit.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * Created by shivam on 01/02/18.
 */

inline fun <reified T : Any> Moshi.toJson(data: T): String {
    val adapter: JsonAdapter<T> = adapter(T::class.java)
    return adapter.toJson(data)
}

inline fun <reified T : Any> Moshi.fromJson(json: String): T {
    val adapter: JsonAdapter<T> = adapter(T::class.java)
    return adapter.fromJson(json)!!
}