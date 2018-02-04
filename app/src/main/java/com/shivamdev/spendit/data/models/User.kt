package com.shivamdev.spendit.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by shivam on 02/02/18.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class User(val userId: String?,
                val name: String?)
    : Parcelable