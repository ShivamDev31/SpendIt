package com.shivamdev.spendit.features.login

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class LoginPresenter @Inject constructor(private val firebaseHelper: FirebaseHelper,
                                         private val userHelper: UserHelper)
    : BasePresenter<LoginView>() {

    fun signInSuccess() {
        updateUsersTable()
        view?.startHomeActivity()
    }

    private fun updateUsersTable() {
        val firebaseUser = firebaseHelper.getFirebaseUser()
        updateLocalPreferences(firebaseUser!!)
        firebaseHelper.updateUser(firebaseUser)
    }

    private fun updateLocalPreferences(user: User) {
        userHelper.saveUser(user)
    }

}