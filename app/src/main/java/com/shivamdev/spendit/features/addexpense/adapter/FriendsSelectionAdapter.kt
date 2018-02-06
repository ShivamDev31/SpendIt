package com.shivamdev.spendit.features.addexpense.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivamdev.spendit.R
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.initials
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_friends_selection.view.*

/**
 * Created by shivam on 03/02/18.
 */
class FriendsSelectionAdapter : RecyclerView.Adapter<FriendsSelectionAdapter.FriendsSelectionHolder>() {

    private var users = mutableListOf<User>()

    private val addFriendSubject = PublishSubject.create<User>()
    private val removeFriendSubject = PublishSubject.create<User>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FriendsSelectionHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_friends_selection,
                parent, false)
        return FriendsSelectionHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: FriendsSelectionHolder?, position: Int) {
        holder?.bind(users[position])
    }

    fun updateData(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun addFriendListener(): Observable<User> = addFriendSubject

    fun removeFriendListener(): Observable<User> = removeFriendSubject

    inner class FriendsSelectionHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.cbFriendSelection
                    .setOnCheckedChangeListener { _, checked ->
                        val user = users[adapterPosition]
                        if (checked) {
                            if (!user.checked) {
                                addFriendSubject.onNext(user)
                            }
                            user.checked = checked
                        } else {
                            user.checked = checked
                            removeFriendSubject.onNext(user)
                        }
                    }
        }

        fun bind(user: User) {
            itemView.tvNameInitials.text = user.name?.initials()
            itemView.tvFriendName.text = user.name
            itemView.cbFriendSelection.isChecked = user.checked
        }

    }

}