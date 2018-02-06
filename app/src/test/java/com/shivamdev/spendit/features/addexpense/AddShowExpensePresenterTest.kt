package com.shivamdev.spendit.features.addexpense

import com.shivamdev.spendit.data.firebase.FirebaseHelper
import com.shivamdev.spendit.data.local.UserHelper
import com.shivamdev.spendit.utils.RxSchedulersOverrideRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
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
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}