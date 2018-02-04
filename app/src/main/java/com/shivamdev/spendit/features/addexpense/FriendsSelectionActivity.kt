package com.shivamdev.spendit.features.addexpense

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.common.constants.SELECTED_USERS
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.features.addexpense.adapter.FriendsSelectionAdapter
import com.shivamdev.spendit.utils.longToast
import com.shivamdev.spendit.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_friends_selection.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by shivam on 03/02/18.
 */
class FriendsSelectionActivity : BaseActivity<FriendsSelectionPresenter>(), FriendsSelectionView {

    private lateinit var adapter: FriendsSelectionAdapter

    private val selectedUsers = ArrayList<User>()

    override fun initView() {
        setupToolbar(toolbar, getString(R.string.select_friends), true)
        setupRecyclerView()
        presenter.getUserData()

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        adapter = FriendsSelectionAdapter()
        rvFriendsSelection.addItemDecoration(DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL))
        rvFriendsSelection.layoutManager = layoutManager
        rvFriendsSelection.adapter = adapter
    }

    override fun showUsers(users: MutableList<User>) {
        adapter.updateData(users)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_friends_selection, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        when (itemId) {
            R.id.menu_item_done -> {
                mapDummyData()
                presenter.friendsSelected(selectedUsers)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mapDummyData() {
        (1..10).filter { it % 2 == 0 }
                .mapTo(selectedUsers) {
                    User("$it", "Shivam $it")
                }
    }

    override fun showSelectedFriendsOnUi() {
        val intent = Intent()
        intent.putParcelableArrayListExtra(SELECTED_USERS, selectedUsers)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showSelectFriendsError() {
        longToast(R.string.expense_friends_empty_error)
    }

    override val layout: Int = R.layout.activity_friends_selection

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}