package com.shivamdev.spendit.features.friends

import com.nhaarman.mockito_kotlin.verify
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.dummy.getFilteredUsers
import com.shivamdev.spendit.dummy.getUnCommonUser
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.dummy.getUsers
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

    private var presenter: FriendsPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendsPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGettingFriendsFromFirebaseWithoutFilter() {
        val usersObservable = Observable.just(getUsers())
        `when`(userHelper.getUser()).thenReturn(getUnCommonUser())
        `when`(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserFriends()
        verify(view).showUserFriends(getUsers())
    }

    @Test
    fun testGettingFriendsFromFirebaseWithFilter() {
        val usersObservable = Observable.just(getUsers())
        `when`(userHelper.getUser()).thenReturn(getUser())
        `when`(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserFriends()
        verify(view).showUserFriends(getFilteredUsers())
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}