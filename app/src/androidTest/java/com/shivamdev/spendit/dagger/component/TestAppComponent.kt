package com.shivamdev.spendit.dagger.component

import com.shivamdev.spendit.dagger.module.TestAppModule
import com.shivamdev.spendit.di.component.AppComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created by shivam on 07/02/18.
 */
@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent {

}