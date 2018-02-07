package com.shivamdev.spendit.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shivamdev.spendit.common.constants.USER_TABLE
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.exts.getCompletable
import com.shivamdev.spendit.exts.getObservable
import com.shivamdev.spendit.exts.mergeDocument
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
class FirebaseHelper @Inject constructor(private val firestore: FirebaseFirestore) {

    fun getFirebaseUser(): User? {
        val firebaseUser = FirebaseAuth.getInstance()?.currentUser
        return if (firebaseUser == null) {
            null
        } else {
            User(firebaseUser.uid, firebaseUser.displayName)
        }
    }

    fun updateUser(user: User): Completable = firestore
            .collection(USER_TABLE).document("${user.userId}").mergeDocument(user)

    fun getUserDetails(userId: String): Observable<User> = firestore
            .collection(USER_TABLE).document(userId).getObservable()


    fun getAllUsers(): Observable<List<User>> = firestore.collection(USER_TABLE).getObservable()


    fun addExpense(userId: String, expense: Expense) = firestore.collection(userId).add(expense)
            .getCompletable()

    fun getUserExpenses(userId: String?): Observable<List<Expense>> = firestore
            .collection(userId!!).orderBy("timeStamp", Query.Direction.DESCENDING).getObservable()

}