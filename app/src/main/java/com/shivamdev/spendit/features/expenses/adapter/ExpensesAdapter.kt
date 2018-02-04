package com.shivamdev.spendit.features.expenses.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.utils.initials
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_expenses.view.*

/**
 * Created by shivam on 02/02/18.
 */
class ExpensesAdapter : RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>() {

    private val expenses = mutableListOf<Expense>()

    private val clickSubject = PublishSubject.create<Expense>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ExpensesHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_expenses, parent,
                false)
        return ExpensesHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensesHolder?, position: Int) {
        holder?.bind(expenses[position])
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    fun updateUserExpenses(expenses: MutableList<Expense>) {
        this.expenses.clear()
        this.expenses.addAll(expenses)
        notifyDataSetChanged()
    }

    fun getClickEvent(): PublishSubject<Expense> = clickSubject

    inner class ExpensesHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                clickSubject.onNext(expenses[adapterPosition])
            }
        }

        fun bind(expense: Expense) {
            itemView.tvExpenseInitials.text = expense.purpose.initials()
            itemView.tvExpenseName.text = expense.purpose
            itemView.tvExpenseAmount.text = itemView.context
                    .getString(R.string.rupee_amount_int, expense.amount)
        }

    }

}