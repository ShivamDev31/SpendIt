package com.shivamdev.spendit.common.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.common.SpendApp
import com.shivamdev.spendit.common.mvp.Presenter
import com.shivamdev.spendit.di.component.DaggerFragmentComponent
import com.shivamdev.spendit.di.component.FragmentComponent
import javax.inject.Inject

/**
 * Created by shivam on 02/02/18.
 */
abstract class BaseFragment<P : Presenter<*>> : Fragment() {

    @Inject
    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(setupFragmentComponent())
        attachView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun initView()

    protected abstract val layout: Int

    protected abstract fun inject(fragmentComponent: FragmentComponent)

    private fun setupFragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
                .appComponent(SpendApp.instance.component)
                .build()
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