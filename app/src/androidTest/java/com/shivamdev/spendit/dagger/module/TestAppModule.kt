package com.shivamdev.spendit.dagger.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.PrefHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

/**
 * Created by shivam on 07/02/18.
 */
@Module
class TestAppModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences {
        return mock(SharedPreferences::class.java)
    }

    @Provides
    @Singleton
    fun providePrefHelper(): PrefHelper {
        return mock(PrefHelper::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseHelper(): FirebaseHelper {
        return mock(FirebaseHelper::class.java)
    }

    @Provides
    @Singleton
    fun provideUserHelper(): UserHelper {
        return mock(UserHelper::class.java)
    }

}