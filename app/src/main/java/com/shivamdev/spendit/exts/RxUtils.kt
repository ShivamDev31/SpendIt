package com.shivamdev.spendit.exts

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by shivam on 04/02/18.
 */

fun <T> transformObservable(): ObservableTransformer<T, T> = ObservableTransformer {
    it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun transformCompletable(): CompletableTransformer = CompletableTransformer {
    it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}