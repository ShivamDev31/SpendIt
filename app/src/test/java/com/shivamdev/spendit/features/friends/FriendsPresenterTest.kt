package com.shivamdev.spendit.features.friends

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 06/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class FriendsPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private val usersObservable = Observable.just(getUsers())

    private var firebaseHelper: FirebaseHelper = mock {
        on { getAllUsers() } doReturn usersObservable
    }

    private var userHelper: UserHelper = mock {
        on { getUser() } doReturn getUser()
    }

    private var view: FriendsView = mock()

    private var presenter: FriendsPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendsPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGettingFriendsFromFirebaseWithoutFilter() {
        whenever(userHelper.getUser()).thenReturn(getUnCommonUser())
        whenever(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserFriends()
        verify(view).showUserFriends(getUsers())
    }

    @Test
    fun testGettingFriendsFromFirebaseWithFilter() {
        presenter?.getUserFriends()
        verify(view).showUserFriends(getFilteredUsers())
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}