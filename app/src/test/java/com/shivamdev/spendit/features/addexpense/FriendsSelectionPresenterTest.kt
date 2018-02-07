package com.shivamdev.spendit.features.addexpense

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.dummy.*
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 07/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class FriendsSelectionPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private val usersObservable = Observable.just(getUsers())

    private var firebaseHelper: FirebaseHelper = mock {
        on { getAllUsers() } doReturn usersObservable
    }
    private var userHelper: UserHelper = mock {
        on { getUser() } doReturn getUnCommonUser()
    }

    private var view: FriendsSelectionView = mock()

    private var presenter: FriendsSelectionPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendsSelectionPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserDataWhenCurrentUserIsNotPresent() {
        presenter?.getUserData(getSelectedUsers() as ArrayList)
        verify(view).showUsers(getUsers() as ArrayList)
    }

    @Test
    fun testGetUserDataWhenCurrentUserIsPresent() {
        val usersObservable = Observable.just(getUsers())
        whenever(userHelper.getUser()).thenReturn(getUser())
        whenever(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserData(getSelectedUsers() as ArrayList)
        verify(view).showUsers(getFilteredUsers().toMutableList())
    }

    @Test
    fun testIfAtleastOneFriendIsSelected() {
        presenter?.friendsSelected(getUsers() as ArrayList<User>)
        verify(view).showSelectedFriendsOnUi()
    }

    @Test
    fun testIfNoFriendIsSelected() {
        presenter?.friendsSelected(ArrayList())
        verify(view).showSelectFriendsError()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}