package com.shivamdev.spendit.features.home

import android.support.design.widget.BottomNavigationView
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.features.addexpense.AddExpenseActivity
import com.shivamdev.spendit.features.expenses.ExpensesFragment
import com.shivamdev.spendit.features.login.LoginActivity
import com.shivamdev.spendit.features.transactions.TransactionsFragment
import com.shivamdev.spendit.utils.activityStarter
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by shivam on 01/02/18.
 */
class HomeActivity : BaseActivity<HomePresenter>(), HomeView {

    private val TRANSACTIONS_FRAGMENT_TAG = "HOME_TAG"
    private val FRIENDS_FRAGMENT_TAG = "FRIENDS_TAG"

    override fun initView() {
        presenter.checkUserLogin()
        homeBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(R.id.llHomeFragment, TransactionsFragment.newInstance(),
                TRANSACTIONS_FRAGMENT_TAG)
        fabAddExpense.setOnClickListener {
            activityStarter<AddExpenseActivity>()
        }
    }

    override fun startLoginActivity() {
        activityStarter<LoginActivity>()
        finish()
    }

    override fun showUserName(userDetails: String) {
        //tvUserDetails.text = userDetails
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
                replaceFragment(R.id.llHomeFragment, TransactionsFragment.newInstance(), TRANSACTIONS_FRAGMENT_TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(R.id.llHomeFragment, ExpensesFragment.newInstance(), FRIENDS_FRAGMENT_TAG)
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