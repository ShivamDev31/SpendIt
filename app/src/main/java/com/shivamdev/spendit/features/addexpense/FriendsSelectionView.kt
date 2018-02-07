package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BaseView
import com.shivamdev.spendit.data.models.User

/**
 * Created by shivam on 03/02/18.
 */
interface FriendsSelectionView : BaseView {
    fun showUsers(users: MutableList<User>)
    fun showSelectFriendsError()
    fun showSelectedFriendsOnUi()
}