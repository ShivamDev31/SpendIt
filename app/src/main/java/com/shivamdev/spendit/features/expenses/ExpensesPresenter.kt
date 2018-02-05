package com.shivamdev.spendit.features.expenses

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.utils.NoSuchDocumentException
import com.shivamdev.spendit.utils.transformObservable
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
                                .subscribe({ getUserLentBalance() })
                    }
                })
                .compose(transformObservable())
                .subscribe({ balance ->
                    view?.updateUserBalance(balance.userLent)
                }, {
                    Timber.e(it)
                })

        addDisposable(disp)
    }

    fun getUserBorrowBalance() {
        val disp = firebaseHelper.getUserDetails(userHelper.getUser().userId!!)
                .doOnError({ t: Throwable? ->
                    if (t is NoSuchDocumentException) {
                        val user = userHelper.getUser()
                        firebaseHelper.updateUser(user)
                                .subscribe({ getUserBorrowBalance() })
                    }
                })
                .compose(transformObservable())
                .subscribe({ balance ->
                    view?.updateUserBalance(balance.userBorrow)
                }, {
                    Timber.e(it)
                })

        addDisposable(disp)
    }

    fun getExpensesData() {
        val expenses = mutableListOf<Expense>()
        (0..9).mapTo(expenses) {
            Expense("$it", "Shivam $it", (it + 1) * 1000,
                    "Trip to bali $it")
        }
        view?.updateUserExpenses(expenses)
    }

}