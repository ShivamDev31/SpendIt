package com.shivamdev.spendit.features.addexpense

import android.app.Activity
import android.content.Intent
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.common.constants.EXPENSE
import com.shivamdev.spendit.common.constants.SELECTED_USERS
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.utils.*
import kotlinx.android.synthetic.main.activity_add_expense.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by shivam on 01/02/18.
 */
class AddExpenseActivity : BaseActivity<AddExpensePresenter>(), AddExpenseView {

    private val FRIEND_SELECTION_REQUEST_CODE = 9001

    private var amountPaid = 0
    private var selectedUsers = ArrayList<User>()

    private var expense: Expense? = null

    override fun initView() {
        getBundleData()
        if (expense == null) {
            setupToolbar(toolbar, getString(R.string.add_expense), true)
            addExpenseFlow()
        } else {
            setupToolbar(toolbar, getString(R.string.expense_detail), true)
            expenseDetailsFlow()
        }
    }

    private fun addExpenseFlow() {
        llFriendsSelection.setOnClickListener {
            activityStarterForResult<FriendsSelectionActivity>(FRIEND_SELECTION_REQUEST_CODE)
        }

        setupTextValues()
        setupExpenseAmount()
        saveExpense()
    }

    private fun expenseDetailsFlow() {
        etExpenseAmount.setText(expense?.amount.toString())
        etExpenseAmount.isEnabled = false
        etExpensePurpose.setText(expense?.purpose)
        etExpensePurpose.isEnabled = false
        tvPayer.text = expense?.payer
        tvPayerInitials.text = expense?.payer?.initials()
        tvPayerPaidAmount.text = getString(R.string.rupee_amount_int, expense?.amount)
        bSaveExpense.hide()
    }

    private fun getBundleData() {
        expense = intent.getParcelableExtra(EXPENSE)
    }

    private fun setupTextValues() {
        presenter.getUsername()
    }

    override fun setAddPayerName(user: User) {
        tvPayer.text = user.name
        tvPayerInitials.text = user.name?.initials()
    }

    private fun setupExpenseAmount() {
        etExpenseAmount.onTextChange {
            if (it.isNotBlank()) {
                tvPayerPaidAmount.text = getString(R.string.rupee_amount, it)
                amountPaid = it.toInt()
            }
        }
    }

    private fun saveExpense() {
        bSaveExpense.setOnClickListener {
            val purpose = etExpensePurpose.toText()
            presenter.saveExpense(amountPaid, purpose, selectedUsers)
        }
    }

    override fun expenseSaved() {
        longToast(R.string.expense_saved)
        finish()
    }

    override fun showAmountEmptyError() {
        longToast(R.string.expense_amount_empty_error)
    }

    override fun showPurposeEmptyError() {
        longToast(R.string.expense_purpose_empty_error)
    }

    override fun showFriendsEmptyError() {
        longToast(R.string.expense_friends_empty_error)
        activityStarterForResult<FriendsSelectionActivity>(FRIEND_SELECTION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FRIEND_SELECTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedUsers = data?.extras?.getParcelableArrayList(SELECTED_USERS)!!
            showSelectedFriendsOnUi()

        }
    }

    private fun showSelectedFriendsOnUi() {
        val userNames = StringBuilder()
        for (selectedUser in selectedUsers) {
            userNames.append("${selectedUser.name}, ")
        }

        tvSelectedFriends.clear()
        tvSelectedFriends.text = userNames
    }

    override val layout: Int = R.layout.activity_add_expense

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}