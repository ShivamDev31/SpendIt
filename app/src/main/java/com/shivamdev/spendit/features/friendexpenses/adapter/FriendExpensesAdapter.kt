package com.shivamdev.spendit.features.friendexpenses.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.utils.initials
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by shivam on 04/02/18.
 */
class FriendExpensesAdapter : RecyclerView.Adapter<FriendExpensesAdapter.FriendExpensesHolder>() {

    private val expenses = mutableListOf<Expense>()
    private val clickSubject = PublishSubject.create<Expense>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendExpensesHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_friends, parent,
                false)
        return FriendExpensesHolder(view)
    }

    override fun onBindViewHolder(holder: FriendExpensesHolder?, position: Int) {
        holder?.bind(expenses[position])
    }

    override fun getItemCount() = expenses.size

    fun updateFriendExpenses(expenses: List<Expense>) {
        this.expenses.clear()
        this.expenses.addAll(expenses)
        notifyDataSetChanged()
    }

    fun getClickEvent(): PublishSubject<Expense> = clickSubject

    inner class FriendExpensesHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                clickSubject.onNext(expenses[adapterPosition])
            }
        }

        fun bind(expense: Expense) {
            val purpose = expense.purpose
            itemView.tvOwingStatus.text = expense.owingText
            itemView.tvNameInitials.text = purpose.initials()
            itemView.tvFriendName.text = purpose
            itemView.tvOwingAmount.text = itemView.context.getString(R.string.rupee_amount_int,
                    expense.amountPerUser)
        }
    }

}