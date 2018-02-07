package com.shivamdev.spendit.features.home

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.dummy.USER_ID
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private val userObservable = Observable.just(getUser())

    private var firebaseHelper: FirebaseHelper = mock {
        on { getFirebaseUser() } doReturn getUser()
        on { getUserDetails(USER_ID) } doReturn userObservable
    }

    private var userHelper: UserHelper = mock {
        on { getUser() } doReturn getUser()
    }

    private var view: HomeView = mock()

    private var presenter: HomePresenter? = null

    @Before
    fun setUp() {
        presenter = HomePresenter(userHelper, firebaseHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testCheckIfUserIsLoggedIn() {
        presenter?.checkUserLogin()
        verify(userHelper).saveUser(getUser())
    }

    @Test
    fun testRedirectUserToLoginScreenIfNotLoggedIn() {
        whenever(firebaseHelper.getFirebaseUser()).thenReturn(null)
        presenter?.checkUserLogin()
        verify(view).startLoginActivity()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}