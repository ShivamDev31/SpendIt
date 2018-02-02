package com.shivamdev.spendit.features.details

import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.di.component.ActivityComponent

/**
 * Created by shivam on 01/02/18.
 */
class DetailsActivity : BaseActivity<DetailsPresenter>(), DetailsView {
    override val layout: Int = R.layout.activity_details

    override fun initView() {

    }

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}