package com.shivamdev.spendit.features.addexpense

import com.nhaarman.mockito_kotlin.verify
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 07/02/18.
 */

@RunWith(MockitoJUnitRunner::class)
class FriendsSelectionPresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: FriendsSelectionView

    private var presenter: FriendsSelectionPresenter? = null

    @Before
    fun setUp() {
        presenter = FriendsSelectionPresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
    }

    @Test
    fun testGetUserDataWhenCurrentUserIsNotPresent() {
        val usersObservable = Observable.just(getUsers())
        Mockito.`when`(userHelper.getUser()).thenReturn(getUnCommonUser())
        Mockito.`when`(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
        presenter?.getUserData(getSelectedUsers() as ArrayList)
        verify(view).showUsers(getUsers() as ArrayList)
    }

    @Test
    fun testGetUserDataWhenCurrentUserIsPresent() {
        val usersObservable = Observable.just(getUsers())
        Mockito.`when`(userHelper.getUser()).thenReturn(getUser())
        Mockito.`when`(firebaseHelper.getAllUsers()).thenReturn(usersObservable)
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