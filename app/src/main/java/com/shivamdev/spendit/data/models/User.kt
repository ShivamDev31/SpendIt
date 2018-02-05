package com.shivamdev.spendit.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by shivam on 02/02/18.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class User(val userId: String? = "",
                val name: String? = "",
                val userLent: Int = 0,
                val userBorrow: Int = 0,
                val timeStamp: Long = Date().time)
    : Parcelable