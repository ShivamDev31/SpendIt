package com.shivamdev.spendit.features.expenses

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseFragment
import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.features.expenses.adapter.FriendsAdapter
import kotlinx.android.synthetic.main.fragment_friends.*

/**
 * Created by shivam on 02/02/18.
 */
class FriendsFragment : BaseFragment<FriendsPresenter>(), FriendsView {

    private lateinit var adapter: FriendsAdapter

    override fun initView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = FriendsAdapter()
        rvFriends.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        rvFriends.layoutManager = layoutManager
        rvFriends.adapter = adapter
    }

    override val layout: Int = R.layout.fragment_friends

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

    companion object {
        fun newInstance(): FriendsFragment {
            return FriendsFragment()
        }
    }

}