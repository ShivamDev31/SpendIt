package com.shivamdev.spendit.features.transactions

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseFragment
import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.features.transactions.adapter.TransactionsAdapter
import kotlinx.android.synthetic.main.fragment_transactions.*

/**
 * Created by shivam on 02/02/18.
 */
class TransactionsFragment : BaseFragment<TransactionsPresenter>(), TransactionsView {

    private lateinit var adapter: TransactionsAdapter

    override fun initView() {
        setupRadioButtons()
        setupRecyclerView()
    }

    private fun setupRadioButtons() {
        rgGiveTake.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbTake -> {
                    tvBalance.setTextColor(resources.getColor(R.color.green))
                }
                R.id.rbGive -> {
                    tvBalance.setTextColor(resources.getColor(R.color.red))
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = TransactionsAdapter()
        rvTransactions.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        rvTransactions.layoutManager = layoutManager
        rvTransactions.adapter = adapter
    }

    override val layout: Int = R.layout.fragment_transactions

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

    companion object {
        fun newInstance(): TransactionsFragment {
            return TransactionsFragment()
        }
    }

}