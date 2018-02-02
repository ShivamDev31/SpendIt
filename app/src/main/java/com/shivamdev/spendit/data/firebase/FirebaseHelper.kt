package com.shivamdev.spendit.data.firebase

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
class FirebaseHelper @Inject constructor(private val databseReference: DatabaseReference) {

    fun help() {

    }

}