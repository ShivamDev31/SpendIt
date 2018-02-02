package com.shivamdev.spendit.common.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.shivamdev.spendit.common.SpendApp
import com.shivamdev.spendit.common.mvp.Presenter
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.di.component.DaggerActivityComponent
import javax.inject.Inject

/**
 * Created by shivam on 01/02/18.
 */

abstract class BaseActivity<P : Presenter<*>> : AppCompatActivity() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        inject(setupActivityComponent())
        attachView()
        initView()
    }

    protected abstract fun initView()

    protected abstract val layout: Int

    protected abstract fun inject(activityComponent: ActivityComponent)

    private fun setupActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.builder()
                .appComponent(SpendApp.instance.component)
                .build()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutId, fragment, tag)
        fragmentTransaction.commit()
    }

    protected abstract fun attachView()

    private fun detachView() {
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        detachView()
    }
}