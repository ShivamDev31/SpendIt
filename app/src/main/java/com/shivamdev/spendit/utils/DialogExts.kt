package com.shivamdev.spendit.utils

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by shivam on 01/02/18.
 */

private var progressDialog: ProgressDialog? = null

fun Context.showProgressDialog(@StringRes messageId: Int) {
    if (progressDialog == null) {
        progressDialog = ProgressDialog(this)
        progressDialog?.isIndeterminate = true
        progressDialog?.setMessage(resources.getString(messageId))
    }
    progressDialog?.show()
}

fun Context.hideProgressDialog() {
    progressDialog?.dismiss()
}