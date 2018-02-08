package com.shivamdev.spendit.features.addexpense

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.dummy.*
import com.shivamdev.spendit.exts.filterAndRemoveUser
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Created by shivam on 07/02/18.
 */
@RunWith(MockitoJUnitRunner::class)
class AddShowExpensePresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    private var firebaseHelper: FirebaseHelper = mock()

    private var userHelper: UserHelper = mock()

    private var view: AddShowExpenseView = mock()

    private var presenter: AddShowExpensePresenter? = null

    @Before
    fun setUp() {
        presenter = AddShowExpensePresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
        whenever(userHelper.getUser()).thenReturn(getUser())
    }

    @Test
    fun testGetUsernameFromUserHelper() {
        presenter?.getUsername()
        verify(view).setAddPayerName(getUser())
    }

    @Test
    fun checkWhenAmountIsNotValid() {
        presenter?.saveExpense(0, "Purpose", getUsers() as ArrayList<User>)
        verify(view).showAmountEmptyError()
    }

    @Test
    fun checkWhenPurposeIsNotValid() {
        presenter?.saveExpense(10, "", getUsers() as ArrayList<User>)
        verify(view).showPurposeEmptyError()
    }

    @Test
    fun checkWhenSelectedUsersAreNotValid() {
        presenter?.saveExpense(10, "Purpose", ArrayList())
        verify(view).showFriendsEmptyError()
    }

    @Test
    fun testSubmitUsersExpenses() {
        val user = getUnCommonUser()
        val amount = 1000
        val purpose = "Purpose"
        val users = getUsers() as MutableList
        users.add(user)
        val amountPerUser: Int = amount / users.size
        val expense = Expense(user.userId!!, user.name!!, amount, purpose, amountPerUser,
                getExpenseFriendsMap(users))

        val completable = Completable.complete()

        whenever(userHelper.getUser()).thenReturn(user)
        presenter?.saveExpense(amount, purpose, users as ArrayList<User>)
        verify(view).showLoader()
    }

    @Test
    fun testFilterFriendsAndRemoveCurrentUserFromTheFriends() {
        val users = mutableListOf<User>()
        for ((userId, name) in getExpense().friends) {
            val user = User(userId, name, userAmount = 1000)
            users.add(user)
        }
        users.filterAndRemoveUser(getExpense().userId)
        presenter?.filterFriends(getExpense(), 1000)
        verify(view).updateFilteredUsers(users)
    }

    @Test
    fun testShowSelectedFriendsOnUiWithAmount() {
        val selectedFriends = mutableListOf<User>()
        presenter?.friendsSelected(getSelectedUsers() as ArrayList<User>, 1000)
        val amountPerUser: Int = 1000 / (getSelectedUsers().size + 1)

        getSelectedUsers().forEach {
            it.userAmount = amountPerUser
            selectedFriends.add(it)
        }
        verify(view).showSelectedFriendsOnUi(selectedFriends)
    }

    @Test
    fun testShowSelectedFriendsOnUiWithAmountIsZero() {
        val selectedFriends = mutableListOf<User>()
        presenter?.friendsSelected(getSelectedUsers() as ArrayList<User>, 0)
        getSelectedUsers().forEach {
            it.userAmount = 0
            selectedFriends.add(it)
        }
        verify(view).showSelectedFriendsOnUi(selectedFriends)
    }

    @Test
    fun testShowErrorWhenThereAreNoSelectedUsers() {
        val emptyList = ArrayList<User>()
        presenter?.friendsSelected(emptyList, 1000)
        verify(view, never()).showSelectedFriendsOnUi(emptyList)
    }

    @Test
    fun testCheckIfSelectFriendsActivityGettingCalled() {
        presenter?.showSelectFriendsActivity()
        verify(view).showSelectFriendsActivity()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}