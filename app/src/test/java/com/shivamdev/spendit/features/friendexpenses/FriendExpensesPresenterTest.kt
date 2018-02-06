package com.shivamdev.spendit.features.friendexpenses

import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.dummy.USER_ID
import com.shivamdev.spendit.dummy.getExpensesWithText
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */
@RunWith(MockitoJUnitRunner::class)
class FriendExpensesPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: FriendExpensesView

    private var presenter: FriendExpensesPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendExpensesPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserFriendsFromFirebaseSuccess() {
        val expensesObservable = Observable.just(getExpensesWithText())
        `when`(firebaseHelper.getUserExpenses(USER_ID)).thenReturn(expensesObservable)
        `when`(userHelper.getUser()).thenReturn(getUser())
        presenter?.getFriendExpenses(USER_ID)
        verify(view).showLoader()
        verify(view).showFriendExpenses(getExpensesWithText().toMutableList())
        verify(view).hideLoader()
    }

    @Test
    fun testGetUserFriendsFromFirebaseFailure() {
        val exception = NullPointerException("Exception")
        val expensesObservable = Observable.create<List<Expense>>({
            it.onNext(getExpensesWithText())
            it.onError(exception)
        })

        `when`(firebaseHelper.getUserExpenses(USER_ID)).thenReturn(expensesObservable)
        presenter?.getFriendExpenses(USER_ID)

        verify(view).showLoader()
        verify(view, never()).showFriendExpenses(getExpensesWithText().toMutableList())
        verify(view).hideLoader()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}