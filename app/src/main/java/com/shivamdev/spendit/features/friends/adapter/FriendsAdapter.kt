package com.shivamdev.spendit.features.friends.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.initials
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_friends.view.*

/**
 * Created by shivam on 02/02/18.
 */
class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsHolder>() {

    private val friends = mutableListOf<User>()

    private val clickSubject = PublishSubject.create<String>()

    init {
        (1..10).mapTo(friends) {
            User("$it", "Shivam $it")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendsHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_friends, parent,
                false)
        return FriendsHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsHolder?, position: Int) {
        holder?.bind(friends[position])
    }

    override fun getItemCount(): Int {
        return 10
    }

    fun updateTransactions(users: MutableList<User>) {

    }

    fun getFriendCickedEvent() = clickSubject

    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { clickSubject.onNext(friends[adapterPosition].userId!!) }
        }

        fun bind(user: User) {
            itemView.tvNameInitials.text = user.name?.initials()
            itemView.tvFriendName.text = user.name
            //itemView.tvOwingAmount.text = "\u20b9${user.amountBalance}"
        }

    }


}