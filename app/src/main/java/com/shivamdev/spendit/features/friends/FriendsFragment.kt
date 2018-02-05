package com.shivamdev.spendit.features.friends

import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseFragment
import com.shivamdev.spendit.common.constants.USER_ID
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.features.friendexpenses.FriendExpensesActivity
import com.shivamdev.spendit.features.friends.adapter.FriendsAdapter
import kotlinx.android.synthetic.main.fragment_friends.*

/**
 * Created by shivam on 02/02/18.
 */
class FriendsFragment : BaseFragment<FriendsPresenter>(), FriendsView {

    private lateinit var adapter: FriendsAdapter

    override fun initView() {
        setupRecyclerView()
        setupFriendClickListener()
        presenter.getUserFriends()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        adapter = FriendsAdapter()
        rvFriends.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        rvFriends.layoutManager = layoutManager
        rvFriends.adapter = adapter
    }

    override fun showUserFriends(users: List<User>?) {
        adapter.updateUserFriends(users!!)
    }

    private fun setupFriendClickListener() {
        adapter.getFriendClickedEvent()
                .subscribe { friendId ->
                    val intent = Intent(activity, FriendExpensesActivity::class.java)
                    intent.putExtra(USER_ID, friendId)
                    activity?.startActivity(intent)
                }
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