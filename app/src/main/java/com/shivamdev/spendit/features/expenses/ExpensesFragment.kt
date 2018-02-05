package com.shivamdev.spendit.features.expenses

import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseFragment
import com.shivamdev.spendit.common.constants.EXPENSE
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.features.addexpense.AddShowExpenseActivity
import com.shivamdev.spendit.features.expenses.adapter.ExpensesAdapter
import com.shivamdev.spendit.utils.hide
import com.shivamdev.spendit.utils.show
import kotlinx.android.synthetic.main.fragment_expenses.*
import kotlinx.android.synthetic.main.progress_layout.*

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesFragment : BaseFragment<ExpensesPresenter>(), ExpensesView {

    private lateinit var adapter: ExpensesAdapter

    override fun initView() {
        setupRadioButtons()
        setupRecyclerView()
        presenter.getUserLentBalance()
        setupExpenseClickListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.getExpensesData()
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

    override fun updateUserBalance(balance: Int, netBalance: Int) {
        tvBalance.text = getString(R.string.rupee_amount_int, balance)
        if (netBalance >= 0) {
            tvNetBalance.setTextColor(resources.getColor(R.color.green))
        } else {
            tvNetBalance.setTextColor(resources.getColor(R.color.red))
        }
        tvNetBalance.text = getString(R.string.rupee_net_balance_int, Math.abs(-netBalance))
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

    override fun showNoExpensesMessage() {
        rvTransactions.hide()
        tvNoExpenses.show()
    }

    private fun setupExpenseClickListener() {
        adapter.getClickEvent()
                .subscribe { expense ->
                    val intent = Intent(activity, AddShowExpenseActivity::class.java)
                    intent.putExtra(EXPENSE, expense)
                    activity?.startActivity(intent)
                }
    }

    override fun showLoader() {
        progressBar.show()
    }

    override fun hideLoader() {
        progressBar.hide()
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