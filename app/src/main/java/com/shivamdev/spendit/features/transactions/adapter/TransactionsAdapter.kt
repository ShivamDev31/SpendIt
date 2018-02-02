package com.shivamdev.spendit.features.transactions.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import kotlinx.android.synthetic.main.item_transactions.view.*

/**
 * Created by shivam on 02/02/18.
 */
class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {

    private val list = mutableListOf<Pair<String, String>>()

    init {
        for (i in 0..9) {
            val pair = Pair("Title $i", "\u20b9${(i + 1) * 1000}")
            list.add(pair)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionsHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_transactions, parent,
                false)
        return TransactionsHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionsHolder?, position: Int) {
        holder?.bind(list[position])
    }

    override fun getItemCount(): Int {
        return 10
    }

    fun updateTransactions() {

    }

    inner class TransactionsHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pair: Pair<String, String>) {
            itemView.tvExpenseName.text = pair.first
            itemView.tvExpenseAmount.text = pair.second
        }

    }

}