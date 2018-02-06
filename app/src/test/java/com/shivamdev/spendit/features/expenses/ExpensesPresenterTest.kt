package com.shivamdev.spendit.features.expenses

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
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class ExpensesPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: ExpensesView

    private var presenter: ExpensesPresenter? = null

    @Before
    fun setUp() {
        presenter = ExpensesPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserLentBalance() {
        val userObservable = Observable.just(getUser())
        `when`(userHelper.getUser()).thenReturn(getUser())
        `when`(firebaseHelper.getUserDetails(USER_ID)).thenReturn(userObservable)
        presenter?.getUserLentBalance()
        verify(view).updateUserBalance(100, 50)
    }

    @Test
    fun testGetUserBorrowBalance() {
        val userObservable = Observable.just(getUser())
        `when`(userHelper.getUser()).thenReturn(getUser())
        `when`(firebaseHelper.getUserDetails(USER_ID)).thenReturn(userObservable)
        presenter?.getUserBorrowBalance()
        verify(view).updateUserBalance(50, 50)
    }

    @Test
    fun testGetUserBalanceWhenBorrowIsMoreThanLent() {
        val userObservable = Observable.just(getUnCommonUser())
        `when`(userHelper.getUser()).thenReturn(getUnCommonUser())
        `when`(firebaseHelper.getUserDetails(UNCOMMON_USER_ID)).thenReturn(userObservable)
        presenter?.getUserLentBalance()
        verify(view).updateUserBalance(50, -50)
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}