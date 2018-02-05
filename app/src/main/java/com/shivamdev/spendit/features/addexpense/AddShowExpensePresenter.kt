package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.filterAndRemoveUser
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class AddShowExpensePresenter @Inject constructor(private val userHelper: UserHelper,
                                                  private val firebaseHelper: FirebaseHelper)
    : BasePresenter<AddShowExpenseView>() {

    fun getUsername() {
        val user = userHelper.getUser()
        view?.setAddPayerName(user)
    }

    fun saveExpense(amountPaid: Int, purpose: String?, selectedUsers: ArrayList<User>) {
        if (validateUserInputs(amountPaid, purpose, selectedUsers)) return

        submitUserExpenses(amountPaid, selectedUsers, purpose)
    }

    private fun validateUserInputs(amountPaid: Int, purpose: String?, selectedUsers: ArrayList<User>): Boolean {
        if (amountPaid <= 0) {
            view?.showAmountEmptyError()
            return true
        }

        if (purpose.isNullOrBlank()) {
            view?.showPurposeEmptyError()
            return true
        }

        if (selectedUsers.size <= 0) {
            view?.showFriendsEmptyError()
            return true
        }
        return false
    }

    private fun submitUserExpenses(amountPaid: Int, selectedUsers: ArrayList<User>, purpose: String?) {
        view?.showLoader()
        val payer = userHelper.getUser()
        selectedUsers.add(payer)
        val amountPerUser: Int = amountPaid / selectedUsers.size

        val expense = Expense(payer.userId!!, payer.name!!, amountPaid, purpose!!, amountPerUser,
                getExpenseFriendsMap(selectedUsers))
        selectedUsers.forEach {
            updateUserBalance(it, expense)
            updateUserExpense(it.userId!!, expense)
        }

    }

    private fun updateUserExpense(userId: String, expense: Expense) {
        val disp = firebaseHelper.addExpense(userId, expense)
                .doOnComplete { view?.expenseSaved() }
                .subscribe({ Timber.i("Expense added successfully") },
                        { Timber.e(it) })
        addDisposable(disp)
    }

    private fun updateUserBalance(user: User, expense: Expense) {
        if (user.userId == expense.userId) {
            user.userLent += (expense.amountPerUser * (expense.friends.size - 1))
        } else {
            user.userBorrow += (expense.amountPerUser * (expense.friends.size - 1))
        }
        val disp = firebaseHelper.updateUser(user)
                .subscribe({ Timber.i("Expense added successfully") },
                        { Timber.e(it) })
        addDisposable(disp)
    }

    private fun getExpenseFriendsMap(selectedUsers: ArrayList<User>): MutableMap<String, String> {
        val expenseFriendsMap = mutableMapOf<String, String>()
        selectedUsers.forEach {
            expenseFriendsMap[it.userId!!] = it.name!!
        }
        return expenseFriendsMap
    }

    fun filterFriends(friends: Map<String, String>?) {
        val users = mutableListOf<User>()
        for ((userId, name) in friends!!) {
            val user = User(userId, name)
            users.add(user)
        }
        users.filterAndRemoveUser(userHelper.getUser().userId!!)
        view?.updateFilteredUsers(users)
    }

}