package com.socialx.android.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.socialx.android.MainActivity
import com.socialx.android.R
import kotlinx.android.synthetic.main.activity_login.*


const val RC_SIGN_IN = 123
open class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInWithGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.red)
        SignupButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
        tv_forgotPassword.setOnClickListener(this)
        login_btn.setOnClickListener(this)
        tv_register_user.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null) {
            when(view.id) {
                R.id.tv_forgotPassword -> {
                    startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
                }
                R.id.login_btn -> {
                    loginRegisteredUser()
                }
                R.id.tv_register_user -> {
                    startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
                    finish()
                }
            }
        }
    }
    fun validateLoginDetails() : Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_password), true)
                false
            } else -> {
                true
            }
        }
    }
    private fun loginRegisteredUser() {
        if(validateLoginDetails()) {
            showProgressDialog()
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if(task.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        showErrorFunction("You logged in successfully", false)
                    } else {
                        showErrorFunction(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}