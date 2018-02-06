package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.common.mvp.BasePresenter
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.exts.transformObservable
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by shivam on 03/02/18.
 */
class FriendsSelectionPresenter @Inject constructor(private val userHelper: UserHelper,
                                                    private val firebaseHelper: FirebaseHelper)
    : BasePresenter<FriendsSelectionView>() {

    fun getUserData(selectedUsers: ArrayList<User>?) {
        val currentUser = userHelper.getUser()

        val disp = firebaseHelper.getAllUsers()
                .compose(transformObservable())
                .subscribe({ users ->
                    val friends = users.filter { it.userId != currentUser.userId }.toMutableList()
                    selectedAlreadySelectedUsers(friends, selectedUsers)
                }, { Timber.e(it) })

        addDisposable(disp)
    }

    private fun selectedAlreadySelectedUsers(friends: MutableList<User>, selectedUsers: ArrayList<User>?) {
        val checkedFriends = mutableListOf<User>()
        friends.forEach { friend ->
            selectedUsers?.forEach { user ->
                if (friend.userId == user.userId) {
                    friend.checked = true
                }
            }
            checkedFriends.add(friend)
        }
        view?.showUsers(checkedFriends)
    }

    fun friendsSelected(selectedUsers: ArrayList<User>) {
        if (selectedUsers.size <= 0) {
            view?.showSelectFriendsError()
        } else {
            view?.showSelectedFriendsOnUi()
        }
    }

}