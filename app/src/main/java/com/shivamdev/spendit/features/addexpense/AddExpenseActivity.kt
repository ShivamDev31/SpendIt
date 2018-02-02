package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.di.component.ActivityComponent

/**
 * Created by shivam on 01/02/18.
 */
class AddExpenseActivity : BaseActivity<AddExpensePresenter>(), AddExpenseView {
    override val layout: Int = R.layout.activity_add_expense

    override fun initView() {

    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}