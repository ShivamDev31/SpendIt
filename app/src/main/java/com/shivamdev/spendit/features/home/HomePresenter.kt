package com.shivamdev.spendit.features.home

import com.google.firebase.auth.FirebaseAuth
import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.exts.transformObservable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class HomePresenter @Inject constructor(private val userHelper: UserHelper,
                                        private val firebaseHelper: FirebaseHelper)
    : BasePresenter<HomeView>() {

    fun checkUserLogin() {
        val user = firebaseHelper.getFirebaseUser()
        if (user == null) {
            view?.startLoginActivity()
        } else {
            syncUserData()
        }
    }

    private fun syncUserData() {
        compositeDisposable += firebaseHelper.getUserDetails(userHelper.getUser().userId!!)
                .compose(transformObservable())
                .subscribe({
                    userHelper.saveUser(it)
                }, Timber::e)
    }

    fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        userHelper.clear()
        checkUserLogin()
    }

}