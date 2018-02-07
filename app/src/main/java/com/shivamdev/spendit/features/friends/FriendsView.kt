package com.shivamdev.spendit.features.friends

import com.shivamdev.spendit.common.mvp.BaseView
import com.shivamdev.spendit.data.models.User

/**
 * Created by shivam on 02/02/18.
 */
interface FriendsView : BaseView {
    fun showUserFriends(users: List<User>?)
}