package com.shivamdev.spendit.features.login

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Completable
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
class LoginPresenterTest {


    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private val completable = Completable.complete()

    private var firebaseHelper: FirebaseHelper = mock {
        on { getFirebaseUser() } doReturn getUser()
        on { updateUser(getUser()) } doReturn completable
    }

    private var userHelper: UserHelper = mock()

    private var view: LoginView = mock()

    private var presenter: LoginPresenter? = null

    @Before
    fun setUp() {
        presenter = LoginPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun checkUserSignInSuccessAndUserDetailsSaved() {
        presenter?.signInSuccess()
        verify(userHelper).saveUser(getUser())
        verify(firebaseHelper).updateUser(getUser())
        verify(view).startHomeActivity()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}