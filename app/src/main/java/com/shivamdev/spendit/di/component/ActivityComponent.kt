package com.shivamdev.spendit.di.component

import com.shivamdev.spendit.di.module.ActivityModule
import com.shivamdev.spendit.di.scope.PerActivity
import com.shivamdev.spendit.features.addexpense.AddExpenseActivity
import com.shivamdev.spendit.features.details.DetailsActivity
import com.shivamdev.spendit.features.home.HomeActivity
import com.shivamdev.spendit.features.login.LoginActivity
import dagger.Component

/**
 * Created by shivam on 01/02/18.
 */

@PerActivity
@Component(dependencies = [(AppComponent::class)],
        modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(addExpenseActivity: AddExpenseActivity)
    fun inject(detailsActivity: DetailsActivity)


}