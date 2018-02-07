package com.shivamdev.spendit.features.expenses

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.dummy.UNCOMMON_USER_ID
import com.shivamdev.spendit.dummy.USER_ID
import com.shivamdev.spendit.dummy.getUnCommonUser
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class ExpensesPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private val userObservable = Observable.just(getUser())

    private var firebaseHelper: FirebaseHelper = mock {
        on { getUserDetails(USER_ID) } doReturn userObservable
    }

    private var userHelper: UserHelper = mock {
        on { getUser() } doReturn getUser()
    }

    private var view: ExpensesView = mock()

    private var presenter: ExpensesPresenter? = null

    @Before
    fun setUp() {
        presenter = ExpensesPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserLentBalance() {
        presenter?.getUserLentBalance()
        verify(view).updateUserBalance(100, 50)
    }

    @Test
    fun testGetUserBorrowBalance() {
        presenter?.getUserBorrowBalance()
        verify(view).updateUserBalance(50, 50)
    }

    @Test
    fun testGetUserBalanceWhenBorrowIsMoreThanLent() {
        val userObservable = Observable.just(getUnCommonUser())
        whenever(userHelper.getUser()).thenReturn(getUnCommonUser())
        whenever(firebaseHelper.getUserDetails(UNCOMMON_USER_ID)).thenReturn(userObservable)
        presenter?.getUserLentBalance()
        verify(view).updateUserBalance(50, -50)
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}