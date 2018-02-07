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
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            view?.startLoginActivity()
        }
        syncUserData()
    }

    private fun syncUserData() {
        val disp = firebaseHelper.getUserDetails(userHelper.getUser().userId!!)
                .compose(transformObservable())
                .subscribe({
                    userHelper.saveUser(it)
                }, { Timber.e(it) })

        compositeDisposable += disp
    }

    fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        userHelper.clear()
        checkUserLogin()
    }

}