package com.shivamdev.spendit.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shivamdev.spendit.common.constants.USER_TABLE
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.getCompletable
import com.shivamdev.spendit.utils.getObservable
import com.shivamdev.spendit.utils.mergeDocument
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Singleton
class FirebaseHelper @Inject constructor(private val firestore: FirebaseFirestore) {

    fun updateUser(user: User): Completable {
        return firestore.collection(USER_TABLE).document("${user.userId}").mergeDocument(user)
    }

    fun getUserDetails(userId: String): Observable<User> {
        return firestore.collection(USER_TABLE).document(userId).getObservable()
    }

    fun getAllUsers(): Observable<List<User>> {
        return firestore.collection(USER_TABLE).getObservable()
    }

    fun addExpense(userId: String, expense: Expense): Completable {
        return firestore.collection(userId).add(expense).getCompletable()
    }

    fun getUserExpenses(userId: String?): Observable<List<Expense>> {
        return firestore.collection(userId!!).orderBy("timeStamp", Query.Direction.DESCENDING).getObservable()
    }

}