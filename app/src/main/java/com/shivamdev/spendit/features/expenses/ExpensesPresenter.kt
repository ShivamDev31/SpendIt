package com.shivamdev.spendit.features.expenses

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.models.Expense
import javax.inject.Inject

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesPresenter @Inject constructor(private val firebaseHelper: FirebaseHelper)
    : BasePresenter<ExpensesView>() {

    fun getUserTakeBalance() {
        //firebaseHelper.getUserData(FirebaseAuth.getInstance().currentUser?.uid)
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