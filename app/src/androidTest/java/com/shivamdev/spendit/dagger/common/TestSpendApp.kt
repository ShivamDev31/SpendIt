package com.shivamdev.spendit.dagger.common

import com.shivamdev.spendit.common.SpendApp
import com.shivamdev.spendit.dagger.component.DaggerTestAppComponent
import com.shivamdev.spendit.dagger.component.TestAppComponent
import com.shivamdev.spendit.dagger.module.TestAppModule

/**
 * Created by shivam on 07/02/18.
 */
class TestSpendApp : SpendApp() {


    override fun setupDagger(): TestAppComponent = DaggerTestAppComponent
            .builder()
            .testAppModule(TestAppModule(this))
            .build()

    companion object {
        lateinit var instance: TestSpendApp
            private set
    }

}