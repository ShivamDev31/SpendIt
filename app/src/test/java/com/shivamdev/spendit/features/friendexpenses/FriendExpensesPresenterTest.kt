package com.shivamdev.spendit.features.friendexpenses

import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
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

    @Mock
    private lateinit var expenses: List<Expense>

    private var presenter: FriendExpensesPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendExpensesPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserFriendsFromFirebaseSuccess() {
        val expensesObservable = Observable.just(expenses)
        `when`(firebaseHelper.getUserExpenses("userId")).thenReturn(expensesObservable)

        presenter?.getFriendExpenses("userId")
        verify(view).showLoader()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}