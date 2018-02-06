package com.shivamdev.spendit.exts

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by shivam on 04/02/18.
 */

class NoSuchDocumentException : Exception("There is no document at the given DocumentReference")

inline fun <reified T> DocumentReference.getObservable(): Observable<T> {
    return Observable.create { emitter ->
        val listener = addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            documentSnapshot?.let {
                if (documentSnapshot.exists()) {
                    try {
                        emitter.onNext(documentSnapshot.toObject(T::class.java))
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                } else {
                    emitter.onError(NoSuchDocumentException())
                }
            }
            firebaseFirestoreException?.let { emitter.onError(it) }
        }
        emitter.setCancellable { listener.remove() }
    }
}

inline fun <reified T> CollectionReference.getObservable(): Observable<List<T>> {
    return Observable.create { emitter ->
        val listener = addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            querySnapshot?.let {
                try {
                    emitter.onNext(it.toObjects(T::class.java))
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
            firebaseFirestoreException?.let { emitter.onError(it) }
        }
        emitter.setCancellable { listener.remove() }
    }
}

inline fun <reified T> Query.getObservable(): Observable<List<T>> {
    return Observable.create { emitter ->
        val listener = addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            querySnapshot?.let {
                try {
                    emitter.onNext(it.toObjects(T::class.java))
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
            firebaseFirestoreException?.let { emitter.onError(it) }
        }
        emitter.setCancellable { listener.remove() }
    }
}

fun <T : Any> DocumentReference.mergeDocument(item: T): Completable {
    return Completable.create { emitter ->
        set(item, SetOptions.merge())
                .addOnCompleteListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(it) }
    }
}

fun <T : Any> Task<T>.getCompletable(): Completable {
    return Completable.create { emitter ->
        addOnSuccessListener { emitter.onComplete() }
        addOnFailureListener { emitter.onError(it) }
    }
}