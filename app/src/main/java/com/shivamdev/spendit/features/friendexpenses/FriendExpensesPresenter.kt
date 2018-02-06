package com.shivamdev.spendit.features.friendexpenses

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.exts.transformObservable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class FriendExpensesPresenter @Inject constructor(private val firebaseHelper: FirebaseHelper,
                                                  private val userHelper: UserHelper)
    : BasePresenter<FriendExpensesView>() {


    fun getFriendExpenses(friendId: String) {
        view?.showLoader()
        firebaseHelper.getUserExpenses(userHelper.getUser().userId)
                .compose(transformObservable())
                .subscribe({
                    sortExpensesBasedOnFriends(it, friendId)
                }, { Timber.e(it) })
    }

    private fun sortExpensesBasedOnFriends(expenses: List<Expense>?, friendId: String) {
        val friendExpenses = mutableListOf<Expense>()
        val currentUser = userHelper.getUser().userId
        expenses?.forEach {
            for ((userId, _) in it.friends) {
                if (currentUser == it.userId) {
                    it.owingText = "You lent"
                } else {
                    it.owingText = "You borrowed"
                }
                if (friendId == userId) {
                    friendExpenses.add(it)
                }
            }
        }

        view?.showFriendExpenses(friendExpenses)
        view?.hideLoader()
    }

}