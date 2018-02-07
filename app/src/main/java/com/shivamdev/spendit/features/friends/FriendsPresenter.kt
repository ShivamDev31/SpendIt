package com.shivamdev.spendit.features.friends

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.exts.transformObservable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 02/02/18.
 */
class FriendsPresenter @Inject constructor(private val firebaseHelper: FirebaseHelper,
                                           private val userHelper: UserHelper) : BasePresenter<FriendsView>() {

    fun getUserFriends() {
        compositeDisposable += firebaseHelper.getAllUsers()
                .compose(transformObservable())
                .subscribe({ users ->
                    val friends = users.filter { it.userId != userHelper.getUser().userId }
                            .toMutableList()
                    view?.showUserFriends(friends)
                }, Timber::e)
    }

}