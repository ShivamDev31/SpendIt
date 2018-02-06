package com.shivamdev.spendit.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by shivam on 02/02/18.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class User(val userId: String? = "",
                val name: String? = "",
                var userLent: Int = 0,
                var userBorrow: Int = 0,
                var checked: Boolean = false,
                @Exclude var userAmount: Int? = 0,
                val timeStamp: Long = Date().time)
    : Parcelable {
    override fun equals(other: Any?): Boolean {
        val user = other as User
        if (this.userId == user.userId) {
            return true
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}