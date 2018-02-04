package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import java.util.*
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class AddExpensePresenter @Inject constructor(private val userHelper: UserHelper,
                                              private val firebaseHelper: FirebaseHelper)
    : BasePresenter<AddExpenseView>() {

    fun getUsername() {
        val user = userHelper.getUser()
        view?.setAddPayerName(user)
    }

    fun saveExpense(amountPaid: Int, purpose: String?, selectedUsers: ArrayList<User>) {
        if (amountPaid <= 0) {
            view?.showAmountEmptyError()
            return
        }

        if (purpose.isNullOrBlank()) {
            view?.showPurposeEmptyError()
            return
        }

        if (selectedUsers.size <= 0) {
            view?.showFriendsEmptyError()
            return
        }

        view?.expenseSaved()
    }

}