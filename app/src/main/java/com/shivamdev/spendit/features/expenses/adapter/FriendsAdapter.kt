package com.shivamdev.spendit.features.expenses.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.initials
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by shivam on 02/02/18.
 */
class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsHolder>() {

    private val list = mutableListOf<User>()

    init {
        (1..10).mapTo(list) {
            User("Shivam $it",
                    "\u20b9${it * 2000}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendsHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_friends, parent,
                false)
        return FriendsHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsHolder?, position: Int) {
        holder?.bind(list[position])
    }

    override fun getItemCount(): Int {
        return 10
    }

    fun updateTransactions() {

    }

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            itemView.tvNameInitials.text = user.name.initials().toString()
            itemView.tvFriendName.text = user.name
            itemView.tvOwingAmount.text = user.amountBalance
        }

    }


}