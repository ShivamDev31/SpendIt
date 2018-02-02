package com.shivamdev.spendit.common

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.shivamdev.spendit.di.component.AppComponent
import com.shivamdev.spendit.di.component.DaggerAppComponent
import com.shivamdev.spendit.di.module.AppModule
import timber.log.Timber

/**
 * Created by shivam on 01/02/18.
 */
class SpendApp : Application() {

    lateinit var component: AppComponent
        private set

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupDagger()
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)
    }

    private fun setupDagger() {
        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var instance: SpendApp
            private set
    }

}