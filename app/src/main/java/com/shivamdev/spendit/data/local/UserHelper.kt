package com.shivamdev.spendit.data.local

import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.data.network.MoshiHelper
import com.shivamdev.spendit.exts.fromJson
import com.shivamdev.spendit.exts.toJson
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 04/02/18.
 */

@Singleton
class UserHelper @Inject constructor(private val prefHelper: PrefHelper,
                                     private val moshiHelper: MoshiHelper) {

    private val USER_INFO_KEY = "user_info"

    fun saveUser(user: User?) {
        val userJson = moshiHelper.getMoshi().toJson(user)
        prefHelper.putString(USER_INFO_KEY, userJson)
    }

    fun getUser(): User {
        val userJson = prefHelper.getString(USER_INFO_KEY)
        return moshiHelper.getMoshi().fromJson(userJson)
    }
}