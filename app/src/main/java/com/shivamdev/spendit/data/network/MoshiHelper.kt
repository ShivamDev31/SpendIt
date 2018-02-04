package com.shivamdev.spendit.data.network

import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 04/02/18.
 */
@Singleton
class MoshiHelper @Inject constructor() {

    fun getMoshi(): Moshi {
        return Moshi.Builder().build()
    }

}