package com.shivamdev.spendit.data.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by shivam on 04/02/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Expense(val id: String,
                   val payer: String,
                   val amount: Int,
                   val purpose: String) : Parcelable