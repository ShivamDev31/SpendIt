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
import kotlinx.android.synthetic.main.progress_layout.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by shivam on 01/02/18.
 */
class AddShowExpenseActivity : BaseActivity<AddShowExpensePresenter>(), AddShowExpenseView {

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
            val intent = Intent(this, FriendsSelectionActivity::class.java)
            intent.putParcelableArrayListExtra(SELECTED_USERS, selectedUsers)
            startActivityForResult(intent, FRIEND_SELECTION_REQUEST_CODE)
        }

        setupTextValues()
        setupExpenseAmount()
        saveExpense()
    }

    private fun expenseDetailsFlow() {
        etExpenseAmount.apply {
            setText(expense?.amount.toString())
            isEnabled = false
        }
        etExpensePurpose.apply {
            setText(expense?.purpose)
            isEnabled = false
        }

        tvSelectFriendsTitle.text = getString(R.string.selected_friends)
        tvPayer.text = expense?.name
        tvPayerInitials.text = expense?.name?.initials()
        tvPayerPaidAmount.text = getString(R.string.rupee_amount_int, expense?.amount)
        presenter.filterFriends(expense?.friends)
        bSaveExpense.hide()
    }

    override fun updateFilteredUsers(users: MutableList<User>) {
        selectedUsers.clear()
        selectedUsers.addAll(users)
        showSelectedFriendsOnUi()
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
        progressBar.hide()
        finish()
    }

    override fun showLoader() {
        progressBar.show()
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

        tvSelectedFriends.apply {
            clear()
            text = userNames
        }

    }

    override val layout: Int = R.layout.activity_add_expense

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}