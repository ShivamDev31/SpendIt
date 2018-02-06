package com.shivamdev.spendit.exts

import android.view.View

/**
 * Created by shivam on 04/02/18.
 */
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}