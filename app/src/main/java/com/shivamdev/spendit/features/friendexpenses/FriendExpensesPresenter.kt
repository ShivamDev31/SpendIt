package com.shivamdev.spendit.features.friendexpenses

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.models.Expense
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class FriendExpensesPresenter @Inject constructor() : BasePresenter<FriendExpensesView>() {

    fun getFriendExpenses(friendId: String) {
        val expenses = mutableListOf<Expense>()

        (1..5).mapTo(expenses) {
            Expense("$it", "Shivam C", it * 200, "Going to jammu")
        }

        view?.showFriendExpenses(expenses)
    }

}