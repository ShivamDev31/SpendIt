package com.shivamdev.spendit.features.login

import com.shivamdev.spendit.common.mvp.BaseView

/**
 * Created by shivam on 01/02/18.
 */
interface LoginView : BaseView {
    fun startHomeActivity()
    fun signIn()
}