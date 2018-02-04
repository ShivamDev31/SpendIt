package com.shivamdev.spendit.di.component

import com.shivamdev.spendit.di.module.FragmentModule
import com.shivamdev.spendit.di.scope.PerFragment
import com.shivamdev.spendit.features.expenses.ExpensesFragment
import com.shivamdev.spendit.features.friends.FriendsFragment
import dagger.Component

/**
 * Created by shivam on 02/02/18.
 */

@PerFragment
@Component(dependencies = [(AppComponent::class)],
        modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(expensesFragment: ExpensesFragment)
    fun inject(friendsFragment: FriendsFragment)

}