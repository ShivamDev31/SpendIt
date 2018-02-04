package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BaseView
import com.shivamdev.spendit.data.models.User

/**
 * Created by shivam on 01/02/18.
 */
interface AddExpenseView : BaseView {
    fun setAddPayerName(user: User)
    fun showAmountEmptyError()
    fun showPurposeEmptyError()
    fun showFriendsEmptyError()
    fun expenseSaved()

}