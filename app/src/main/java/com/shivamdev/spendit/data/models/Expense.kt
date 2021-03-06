package com.shivamdev.spendit.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by shivam on 04/02/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Expense(
        val userId: String = "",
        val name: String = "",
        val amount: Int = 0,
        val purpose: String = "",
        var amountPerUser: Int = 0,
        val friends: Map<String, String> = mutableMapOf(),
        var owingText: String = "",
        val timeStamp: Long = Date().time) : Parcelable