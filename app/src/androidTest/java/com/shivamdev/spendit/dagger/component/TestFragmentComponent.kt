package com.shivamdev.spendit.dagger.component

import com.shivamdev.spendit.di.component.FragmentComponent
import com.shivamdev.spendit.di.scope.PerFragment
import com.shivamdev.spendit.features.expenses.ExpensesFragmentTest
import com.shivamdev.spendit.features.friends.FriendsFragmentTest
import dagger.Component

/**
 * Created by shivam on 07/02/18.
 */

@PerFragment
@Component(dependencies = [(TestAppComponent::class)])
interface TestFragmentComponent : FragmentComponent {
    fun inject(expensesFragment: ExpensesFragmentTest)
    fun inject(friendsFragment: FriendsFragmentTest)
}