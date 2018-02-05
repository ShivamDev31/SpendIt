package com.shivamdev.spendit.features.friendexpenses

import com.shivamdev.spendit.common.mvp.BaseView
import com.shivamdev.spendit.data.models.Expense

/**
 * Created by shivam on 01/02/18.
 */
interface FriendExpensesView : BaseView {
    fun showFriendExpenses(expenses: MutableList<Expense>)
    fun showLoader()
    fun hideLoader()

}