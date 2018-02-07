package com.shivamdev.spendit.features.addexpense.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.exts.initials
import kotlinx.android.synthetic.main.item_selected_friends.view.*

/**
 * Created by shivam on 06/02/18.
 */
class SelectedFriendsAdapter : RecyclerView.Adapter<SelectedFriendsAdapter.SelectedFriendsHolder>() {

    private val friends = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectedFriendsHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_selected_friends,
                parent, false)
        return SelectedFriendsHolder(view)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: SelectedFriendsHolder?, position: Int) {
        holder?.bind(friends[position])
    }

    fun updateFriends(friends: MutableList<User>) {
        this.friends.clear()
        this.friends.addAll(friends)
        notifyDataSetChanged()
    }


    inner class SelectedFriendsHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: User) {
            itemView.tvFriendInitials.text = user.name?.initials()
            itemView.tvSelectedFriendName.text = user.name
            itemView.tvSelectedFriendAmount.text = itemView.context
                    .getString(R.string.rupee_amount_int, user.userAmount)
        }

    }
}