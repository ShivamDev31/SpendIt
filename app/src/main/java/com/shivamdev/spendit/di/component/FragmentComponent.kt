package com.shivamdev.spendit.di.component

import com.shivamdev.spendit.di.module.FragmentModule
import com.shivamdev.spendit.di.scope.PerFragment
import com.shivamdev.spendit.features.transactions.TransactionsFragment
import dagger.Component

/**
 * Created by shivam on 02/02/18.
 */

@PerFragment
@Component(dependencies = [(AppComponent::class)],
        modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(transactionsFragment: TransactionsFragment)

}