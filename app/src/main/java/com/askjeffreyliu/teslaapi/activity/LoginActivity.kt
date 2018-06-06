package com.askjeffreyliu.teslaapi.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.content.Intent


import android.os.Build
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity


import android.text.TextUtils


import android.view.View
import android.view.inputmethod.EditorInfo

import android.widget.TextView
import android.widget.Toast

import com.askjeffreyliu.teslaapi.Constant
import com.askjeffreyliu.teslaapi.R
import com.askjeffreyliu.teslaapi.viewmodel.LoginAccessTokenViewModel
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    private var viewModel: LoginAccessTokenViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val data = Prefs.getString(Constant.ACCESS_TOKEN, null)
        if (!TextUtils.isEmpty(data)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        passwordTextInputEditText.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        viewModel = ViewModelProviders.of(this).get(LoginAccessTokenViewModel::class.java)

        signInButton.setOnClickListener { attemptLogin() }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        //        if (mAuthTask != null) {
        //            return;
        //        }

        // Reset errors.
        emailTextInputEditText.error = null
        passwordTextInputEditText.error = null

        // Store values at the time of the login attempt.
        val email = emailTextInputEditText.text.toString()
        val password = passwordTextInputEditText.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            //            passwordTextInputEditText.setError(getString(R.string.error_invalid_password));
            Toast.makeText(this, getString(R.string.error_invalid_password),
                    Toast.LENGTH_SHORT).show()
            focusView = passwordTextInputEditText
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailTextInputEditText.error = getString(R.string.error_field_required)
            focusView = emailTextInputEditText
            cancel = true
        } else if (!isEmailValid(email)) {
            emailTextInputEditText.error = getString(R.string.error_invalid_email)
            focusView = emailTextInputEditText
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            //            mAuthTask = new UserLoginTask(email, password);
            //            mAuthTask.execute((Void) null);
            viewModel!!.getAccessTokenLiveData(email, password)
            setDataListener()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

        scrollView.visibility = if (show) View.GONE else View.VISIBLE
        scrollView.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                scrollView.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        progressBar.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                progressBar.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }

    private fun setDataListener() {
        //        viewModel = ViewModelProviders.of(this).get(LoginAccessTokenViewModel.class);
        viewModel!!.liveData.observe(this, Observer { accessToken ->
            if (!TextUtils.isEmpty(accessToken)) {
                Prefs.putString(Constant.ACCESS_TOKEN, accessToken)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                //                    passwordTextInputEditText.setError(getString(R.string.error_incorrect_password));
                passwordTextInputEditText.requestFocus()

                Toast.makeText(this, getString(R.string.error_incorrect_password),
                        Toast.LENGTH_SHORT).show()

                showProgress(false)
            }
        })
    }
}

