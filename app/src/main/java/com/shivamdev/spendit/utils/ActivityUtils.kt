package com.shivamdev.spendit.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Created by shivam on 02/02/18.
 */

inline fun <reified T : Any> Activity.activityStarter() {
    startActivity(intentFor<T>(this))
}

inline fun <reified T : Any> intentFor(context: Context):
        Intent = Intent(context, T::class.java)


