package com.shivamdev.spendit.features.expenses

import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseFragment
import com.shivamdev.spendit.common.constants.EXPENSE
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.features.addexpense.AddShowShowExpenseActivity
import com.shivamdev.spendit.features.expenses.adapter.ExpensesAdapter
import kotlinx.android.synthetic.main.fragment_expenses.*

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesFragment : BaseFragment<ExpensesPresenter>(), ExpensesView {

    private lateinit var adapter: ExpensesAdapter

    override fun initView() {
        setupRadioButtons()
        setupRecyclerView()
        presenter.getExpensesData()
        presenter.getUserLentBalance()
        setupExpenseClickListener()
    }

    private fun setupRadioButtons() {
        rgGiveTake.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbLent -> {
                    tvBalance.setTextColor(resources.getColor(R.color.green))
                    presenter.getUserLentBalance()
                }
                R.id.rbBorrow -> {
                    tvBalance.setTextColor(resources.getColor(R.color.red))
                    presenter.getUserBorrowBalance()
                }
            }
        }
    }

    override fun updateUserBalance(balance: Int) {
        tvBalance.text = getString(R.string.rupee_amount_int, balance)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = ExpensesAdapter()
        rvTransactions.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        rvTransactions.layoutManager = layoutManager
        rvTransactions.adapter = adapter
    }

    override fun updateUserExpenses(expenses: MutableList<Expense>) {
        adapter.updateUserExpenses(expenses)
    }

    private fun setupExpenseClickListener() {
        adapter.getClickEvent()
                .subscribe { expense ->
                    val intent = Intent(activity, AddShowShowExpenseActivity::class.java)
                    intent.putExtra(EXPENSE, expense)
                    activity?.startActivity(intent)
                }
    }

    override val layout: Int = R.layout.fragment_expenses

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

    companion object {
        fun newInstance(): ExpensesFragment {
            return ExpensesFragment()
        }
    }

}