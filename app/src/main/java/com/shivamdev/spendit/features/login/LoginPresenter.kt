package com.shivamdev.spendit.features.login

import com.shivamdev.spendit.common.mvp.BasePresenter
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {

    fun signInSuccess() {
        view?.startHomeActivity()
    }

}