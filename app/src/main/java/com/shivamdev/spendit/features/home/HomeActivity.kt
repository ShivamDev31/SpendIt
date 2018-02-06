package com.shivamdev.spendit.features.home

import android.support.design.widget.BottomNavigationView
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.features.addexpense.AddShowExpenseActivity
import com.shivamdev.spendit.features.expenses.ExpensesFragment
import com.shivamdev.spendit.features.friends.FriendsFragment
import com.shivamdev.spendit.features.login.LoginActivity
import com.shivamdev.spendit.utils.activityStarter
import com.shivamdev.spendit.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by shivam on 01/02/18.
 */
class HomeActivity : BaseActivity<HomePresenter>(), HomeView {

    private val TRANSACTIONS_FRAGMENT_TAG = "HOME_TAG"
    private val FRIENDS_FRAGMENT_TAG = "FRIENDS_TAG"

    override fun initView() {
        presenter.checkUserLogin()
        setupToolbar(toolbar, showBack = false)
        homeBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(R.id.llHomeFragment, ExpensesFragment.newInstance(),
                TRANSACTIONS_FRAGMENT_TAG)
        fabAddExpense.setOnClickListener {
            activityStarter<AddShowExpenseActivity>()
        }
    }

    override fun startLoginActivity() {
        activityStarter<LoginActivity>()
        finish()
    }

    override val layout: Int = R.layout.activity_home

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(R.id.llHomeFragment, ExpensesFragment.newInstance(), TRANSACTIONS_FRAGMENT_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(R.id.llHomeFragment, FriendsFragment.newInstance(), FRIENDS_FRAGMENT_TAG)
                setupToolbar(toolbar, getString(R.string.friends), false)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                presenter.logoutUser()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}