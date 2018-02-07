package com.shivamdev.spendit.dagger.component

import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.di.scope.PerActivity
import com.shivamdev.spendit.features.addexpense.AddShowExpenseActivityTest
import com.shivamdev.spendit.features.addexpense.FriendsSelectionActivityTest
import com.shivamdev.spendit.features.friendexpenses.FriendExpensesActivityTest
import com.shivamdev.spendit.features.home.HomeActivityTest
import com.shivamdev.spendit.features.login.LoginActivityTest
import dagger.Component

/**
 * Created by shivam on 07/02/18.
 */

@PerActivity
@Component(dependencies = [(TestAppComponent::class)])
interface TestActivityComponent : ActivityComponent {
    fun inject(loginActivity: LoginActivityTest)
    fun inject(homeActivity: HomeActivityTest)
    fun inject(addShowExpenseActivity: AddShowExpenseActivityTest)
    fun inject(friendExpensesActivity: FriendExpensesActivityTest)
    fun inject(friendsSelectionActivity: FriendsSelectionActivityTest)
}