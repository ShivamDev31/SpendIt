package com.shivamdev.spendit.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.shivamdev.spendit.common.constants.USER_TABLE
import com.shivamdev.spendit.data.models.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
class FirebaseHelper @Inject constructor(private val firestore: FirebaseFirestore) {

    fun help() {

    }

    fun updateUser(user: User) {
        firestore.collection(USER_TABLE).document("${user.userId}").set(user)
    }

    fun getUserData(uid: String): User {
        lateinit var user: User
        firestore.collection(USER_TABLE).document(uid).addSnapshotListener { snapshot, _ ->
            user = snapshot.toObject(User::class.java)
        }
        return user
    }

}