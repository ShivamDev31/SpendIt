package com.shivamdev.spendit.features.expenses

import com.shivamdev.spendit.common.mvp.BaseView
import com.shivamdev.spendit.data.models.Expense

/**
 * Created by shivam on 02/02/18.
 */
interface ExpensesView : BaseView {
    fun updateUserExpenses(expenses: MutableList<Expense>) {}
    fun updateUserBalance(balance: Int)

}