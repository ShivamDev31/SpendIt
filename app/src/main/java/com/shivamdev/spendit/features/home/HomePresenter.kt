package com.shivamdev.spendit.features.home

import com.google.firebase.auth.FirebaseAuth
import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class HomePresenter @Inject constructor(private val firebaseHelper: FirebaseHelper) : BasePresenter<HomeView>() {

    fun checkUserLogin() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            view?.startLoginActivity()
        }
    }

    fun logoutUser() {
        FirebaseAuth.getInstance().signOut()

        checkUserLogin()
    }


}