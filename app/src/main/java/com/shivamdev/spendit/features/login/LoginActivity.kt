package com.shivamdev.spendit.features.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shivamdev.spendit.R
import com.shivamdev.spendit.common.base.BaseActivity
import com.shivamdev.spendit.di.component.ActivityComponent
import com.shivamdev.spendit.features.home.HomeActivity
import com.shivamdev.spendit.utils.activityStarter
import com.shivamdev.spendit.utils.hide
import com.shivamdev.spendit.utils.shortToast
import com.shivamdev.spendit.utils.show
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.progress_layout.*
import timber.log.Timber


/**
 * Created by shivam on 01/02/18.
 */
class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    private val RC_SIGN_IN = 9001
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun initView() {
        setupGoogleSignIn()

        sibGoogle.setOnClickListener { signIn() }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        googleSignInClient.signOut()
    }

    override fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Timber.e(e)
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Timber.i("firebaseAuthWithGoogle:$acct.id")
        progressBar.show()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.i("signInWithCredential:success")
                        //val user = firebaseAuth.currentUser
                        presenter.signInSuccess()
                    } else {
                        // If sign in fails, display a message to the user.
                        shortToast(R.string.sign_in_error)
                        Timber.i("signInWithCredential:failure ${task.exception}")
                    }

                    progressBar.hide()
                })
    }


    override fun startHomeActivity() {
        activityStarter<HomeActivity>()
        finish()
    }

    override val layout: Int = R.layout.activity_login

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun attachView() {
        presenter.attachView(this)
    }

}