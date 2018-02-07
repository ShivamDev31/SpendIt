package com.shivamdev.spendit.features.expenses

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.exts.NoSuchDocumentException
import com.shivamdev.spendit.exts.transformCompletable
import com.shivamdev.spendit.exts.transformObservable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesPresenter @Inject constructor(private val firebaseHelper: FirebaseHelper,
                                            private val userHelper: UserHelper)
    : BasePresenter<ExpensesView>() {

    fun getUserLentBalance() {
        val disp = firebaseHelper.getUserDetails(userHelper.getUser().userId!!)
                .doOnError({ t: Throwable? ->
                    if (t is NoSuchDocumentException) {
                        val user = userHelper.getUser()
                        firebaseHelper.updateUser(user)
                                .compose(transformCompletable())
                                .subscribe({ getUserLentBalance() })
                    }
                })
                .compose(transformObservable())
                .subscribe({ user ->
                    val netBalance = user.userLent - user.userBorrow
                    view?.updateUserBalance(user.userLent, netBalance)
                }, {
                    Timber.e(it)
                })

        compositeDisposable += disp
    }

    fun getUserBorrowBalance() {
        val disp = firebaseHelper.getUserDetails(userHelper.getUser().userId!!)
                .doOnError({ t: Throwable? ->
                    if (t is NoSuchDocumentException) {
                        val user = userHelper.getUser()
                        firebaseHelper.updateUser(user)
                                .compose(transformCompletable())
                                .subscribe({ getUserBorrowBalance() })
                    }
                })
                .compose(transformObservable())
                .subscribe({ user ->
                    val netBalance = user.userLent - user.userBorrow
                    view?.updateUserBalance(user.userBorrow, netBalance)
                }, {
                    Timber.e(it)
                })

        compositeDisposable += disp
    }

    fun getExpensesData() {
        view?.showLoader()
        val disp = firebaseHelper.getUserExpenses(userHelper.getUser().userId)
                .compose(transformObservable())
                .subscribe({
                    if (it.isNotEmpty()) {
                        view?.updateUserExpenses(it.toMutableList())
                    } else {
                        view?.showNoExpensesMessage()
                    }
                    view?.hideLoader()
                }, { Timber.e(it) })

        compositeDisposable += disp
    }

}