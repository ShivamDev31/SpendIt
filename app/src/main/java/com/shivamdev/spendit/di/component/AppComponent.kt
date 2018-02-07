package com.shivamdev.spendit.di.component


import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.PrefHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.di.module.AppModule
import com.shivamdev.spendit.di.module.FirebaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class])
interface AppComponent {
    fun prefHelper(): PrefHelper
    fun firebaseHelper(): FirebaseHelper
    fun userHelper(): UserHelper
}
