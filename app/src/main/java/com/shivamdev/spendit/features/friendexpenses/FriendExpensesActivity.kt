package com.shivamdev.spendit.features.friendexpenses

import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.common.constants.EXPENSE
import com.shivamdev.spendit.common.constants.USER_ID
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.features.addexpense.AddShowExpenseActivity
import com.shivamdev.spendit.features.friendexpenses.adapter.FriendExpensesAdapter
import com.shivamdev.spendit.utils.hide
import com.shivamdev.spendit.utils.setupToolbar
import com.shivamdev.spendit.utils.show
import kotlinx.android.synthetic.main.activity_friend_details.*
import kotlinx.android.synthetic.main.progress_layout.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by shivam on 01/02/18.
 */
class FriendExpensesActivity : BaseActivity<FriendExpensesPresenter>(), FriendExpensesView {

    private lateinit var adapter: FriendExpensesAdapter

    private lateinit var friendId: String

    override fun initView() {
        setupToolbar(toolbar, getString(R.string.friend_expenses), true)
        getDataFromBundle()
        setupRecyclerView()
        presenter.getFriendExpenses(friendId)
        setupExpenseClickListener()
    }

    private fun getDataFromBundle() {
        friendId = intent.getStringExtra(USER_ID)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        adapter = FriendExpensesAdapter()
        rvFriendsSplitUp.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvFriendsSplitUp.layoutManager = layoutManager
        rvFriendsSplitUp.adapter = adapter
    }

    override fun showFriendExpenses(expenses: MutableList<Expense>) {
        adapter.updateFriendExpenses(expenses)
    }

    private fun setupExpenseClickListener() {
        adapter.getClickEvent()
                .subscribe { expense ->
                    val intent = Intent(this, AddShowExpenseActivity::class.java)
                    intent.putExtra(EXPENSE, expense)
                    startActivity(intent)
                }
    }

    override fun showLoader() {
        progressBar.show()
    }

    override fun hideLoader() {
        progressBar.hide()
    }

    override val layout: Int = R.layout.activity_friend_details

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}