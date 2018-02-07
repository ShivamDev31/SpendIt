package com.shivamdev.spendit.features.addexpense

import com.nhaarman.mockito_kotlin.verify
import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.data.models.Expense
import com.shivamdev.spendit.data.models.User
import com.shivamdev.spendit.dummy.getExpenseFriendsMap
import com.shivamdev.spendit.dummy.getUnCommonUser
import com.shivamdev.spendit.dummy.getUser
import com.shivamdev.spendit.dummy.getUsers
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by shivam on 07/02/18.
 */
@RunWith(MockitoJUnitRunner::class)
class AddShowExpensePresenterTest {

    @JvmField
    @Rule
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock
    private lateinit var firebaseHelper: FirebaseHelper

    @Mock
    private lateinit var userHelper: UserHelper

    @Mock
    private lateinit var view: AddShowExpenseView

    private var presenter: AddShowExpensePresenter? = null

    @Before
    fun setUp() {
        presenter = AddShowExpensePresenter(firebaseHelper, userHelper)
        presenter?.attachView(view)
        `when`(userHelper.getUser()).thenReturn(getUser())
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
    fun checkSubmitUsersExpenses() {
        val user = getUnCommonUser()
        val amount = 1000
        val purpose = "Purpose"
        val users = getUsers() as MutableList
        users.add(user)
        val amountPerUser: Int = amount / users.size
        val expense = Expense(user.userId!!, user.name!!, amount, purpose, amountPerUser,
                getExpenseFriendsMap(users))

        val completable = Completable.complete()

        `when`(userHelper.getUser()).thenReturn(user)
        `when`(firebaseHelper.addExpense(user.userId!!, expense)).thenReturn(completable)
        presenter?.saveExpense(amount, purpose, users as ArrayList<User>)
        verify(view).showLoader()
        verify(view).hideLoader()
        verify(view).expenseSaved()

    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}