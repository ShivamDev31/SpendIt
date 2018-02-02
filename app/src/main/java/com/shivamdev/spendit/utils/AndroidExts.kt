package com.shivamdev.spendit.utils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.TextView
import android.widget.Toast

/**
 * Created by shivam on 01/02/18.
 */

fun Context.shortToast(@StringRes messageId: Int) {
    Toast.makeText(this, resources.getString(messageId), Toast.LENGTH_SHORT).show()
}

fun Context.longToast(@StringRes messageId: Int) {
    Toast.makeText(this, resources.getString(messageId), Toast.LENGTH_LONG).show()
}

fun Context.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun TextView.toText(): String {
    return text.toString()
}