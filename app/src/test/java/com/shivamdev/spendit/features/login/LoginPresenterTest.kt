package com.shivamdev.spendit.features.login

import com.google.firebase.auth.FirebaseAuth
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */
@RunWith(MockitoJUnitRunner::class)
class LoginPresenterTest {


    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: LoginView

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    private var presenter: LoginPresenter? = null

    @Before
    fun setUp() {
        presenter = LoginPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun checkUserSignInSuccessAndUserDetailsSaved() {
        presenter?.signInSuccess()
        val firebaseUser = firebaseAuth.currentUser
        val user = User(firebaseUser?.uid, firebaseUser?.displayName)
        verify(userHelper, times(1)).saveUser(user)
        verify(firebaseHelper).updateUser(user)
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}