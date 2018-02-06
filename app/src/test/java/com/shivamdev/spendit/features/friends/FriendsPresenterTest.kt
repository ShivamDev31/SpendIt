package com.shivamdev.spendit.features.friends

import com.nhaarman.mockito_kotlin.verify
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class FriendsPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: FriendsView

    @Mock
    private lateinit var users: List<User>

    private var presenter: FriendsPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendsPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGettingFriendsFromFirebaseSuccess() {
        val usersObservable = Observable.just(users)
        `when`(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserFriends()
        verify(view).showUserFriends(users)
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}