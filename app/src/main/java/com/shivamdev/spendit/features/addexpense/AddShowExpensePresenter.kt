package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.exts.filterAndRemoveUser
import com.shivamdev.spendit.exts.transformCompletable
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */
class AddShowExpensePresenter @Inject constructor(private val firebaseHelper: FirebaseHelper,
                                                  private val userHelper: UserHelper)
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

        when {
            amountPaid <= 0 -> {
                view?.showAmountEmptyError()
                return true
            }

            purpose.isNullOrBlank() -> {
                view?.showPurposeEmptyError()
                return true
            }

            selectedUsers.isEmpty() -> {
                view?.showFriendsEmptyError()
                return true
            }

            else -> return false
        }
    }

    private fun submitUserExpenses(amountPaid: Int, selectedUsers: ArrayList<User>, purpose: String?) {
        view?.showLoader()
        val payer = userHelper.getUser()
        selectedUsers.add(payer)
        val amountPerUser: Int = amountPaid / selectedUsers.size

        val expense = Expense(payer.userId!!, payer.name!!, amountPaid, purpose!!, amountPerUser,
                getExpenseFriendsMap(selectedUsers))

        updateUserExpense(selectedUsers, expense)
    }

    private fun updateUserExpense(users: ArrayList<User>, expense: Expense) {
        compositeDisposable += Observable.fromIterable(users)
                .concatMapCompletable {
                    it.checked = false
                    updateUserBalance(it, expense)
                    firebaseHelper.addExpense(it.userId!!, expense)
                }
                .compose(transformCompletable())
                .doOnComplete {
                    view?.hideLoader()
                    view?.expenseSaved()
                }.subscribe({ Timber.i("Expense added successfully") },
                        { Timber.e(it) })

    }

    private fun updateUserBalance(user: User, expense: Expense) {
        if (user.userId == expense.userId) {
            user.userLent += (expense.amountPerUser * (expense.friends.size - 1))
        } else {
            user.userBorrow += expense.amountPerUser
        }
        compositeDisposable += firebaseHelper.updateUser(user)
                .compose(transformCompletable())
                .subscribe({ Timber.i("Expense added successfully") },
                        { Timber.e(it) })
    }

    private fun getExpenseFriendsMap(selectedUsers: ArrayList<User>): MutableMap<String, String> {
        val expenseFriendsMap = mutableMapOf<String, String>()
        selectedUsers.forEach {
            expenseFriendsMap[it.userId!!] = it.name!!
        }
        return expenseFriendsMap
    }

    fun filterFriends(expense: Expense, amountPerUser: Int?) {
        val users = mutableListOf<User>()
        for ((userId, name) in expense.friends) {
            val user = User(userId, name, userAmount = amountPerUser)
            users.add(user)
        }
        users.filterAndRemoveUser(expense.userId)
        view?.updateFilteredUsers(users)
    }

    fun friendsSelected(selectedUsers: ArrayList<User>, amountPaid: Int) {
        val selectedFriends = mutableListOf<User>()
        when {
            selectedUsers.isEmpty() -> return
            else -> {
                val amountPerUser: Int = when {
                    amountPaid <= 0 -> 0
                    else -> amountPaid / (selectedUsers.size + 1)
                }
                selectedUsers.forEach {
                    it.userAmount = amountPerUser
                    selectedFriends.add(it)
                }
                view?.showSelectedFriendsOnUi(selectedFriends)
            }
        }
    }

    fun showSelectFriendsActivity() {
        view?.showSelectFriendsActivity()
    }

}