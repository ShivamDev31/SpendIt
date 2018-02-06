package com.shivamdev.spendit.features.home

import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: HomeView

    @Mock
    private lateinit var user: User

    private var presenter: HomePresenter? = null


    @Before
    fun setUp() {
        presenter = HomePresenter(userHelper, firebaseHelper)
        presenter?.attachView(view)
    }

    @Test
    fun checkIfUserIsLoggedIn() {
        val userObservable = Observable.just(user)
        `when`(firebaseHelper.getUserDetails(anyString())).thenReturn(userObservable)
        presenter?.checkUserLogin()
        verify(userHelper).saveUser(user)

    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}