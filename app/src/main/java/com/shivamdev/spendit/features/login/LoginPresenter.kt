package com.shivamdev.spendit.features.login

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.exts.transformCompletable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
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
        val user = firebaseHelper.getFirebaseUser()
        updateLocalPreferences(user!!)
        compositeDisposable += firebaseHelper.updateUser(user)
                .compose(transformCompletable())
                .subscribe({ Timber.i("User added successfully") }, Timber::e)
    }

    private fun updateLocalPreferences(user: User) {
        userHelper.saveUser(user)
    }

}