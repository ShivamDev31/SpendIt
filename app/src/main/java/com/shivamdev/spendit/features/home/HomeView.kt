package com.shivamdev.spendit.features.home

import com.shivamdev.spendit.common.mvp.BaseView

/**
 * Created by shivam on 01/02/18.
 */
interface HomeView : BaseView {
    fun startLoginActivity()
    fun showUserName(userDetails: String)
}