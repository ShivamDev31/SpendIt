package com.shivamdev.spendit.features.expenses

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesFragment: Fragment() {

    companion object {
        fun newInstance(): ExpensesFragment {
            return ExpensesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)
        return view
    }

}