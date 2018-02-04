package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.models.User
import javax.inject.Inject

/**
 * Created by shivam on 03/02/18.
 */
class FriendsSelectionPresenter @Inject constructor() : BasePresenter<FriendsSelectionView>() {

    fun getUserData() {

        val users = mutableListOf<User>()

        (1..30).mapTo(users) {
            User("$it", "Shivam $it")
        }

        view?.showUsers(users)
    }

    fun friendsSelected(selectedUsers: ArrayList<User>) {
        if (selectedUsers.size <= 0) {
            view?.showSelectFriendsError()
        } else {
            view?.showSelectedFriendsOnUi()
        }
    }

}