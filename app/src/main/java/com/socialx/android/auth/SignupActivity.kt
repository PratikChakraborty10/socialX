package com.socialx.android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.socialx.android.R
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern

class SignupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.red)
        LoginButton.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        register_btn.setOnClickListener {
            val emailText: String = et_mail.text.toString()
            if(EMAIL_ADDRESS_PATTERN.matcher(emailText).matches()) {
                registerUser()
            } else {
                showErrorFunction(resources.getString(R.string.pls_enter_a_valid_email_id), true)
            }
        }
    }
    private fun validateRegisterUserDetail() : Boolean {
        return when {
            TextUtils.isEmpty(et_name.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(et_mail.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_phone.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_number), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorFunction(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorFunction(resources.getString(R.string.err_msg_cb), true)
                false
            } else -> {
                true
            }

        }
    }
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    private fun registerUser() {
        if(validateRegisterUserDetail()) {
            showProgressDialog()
            val email: String = et_mail.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' '}
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        hideProgressDialog()
                        if(task.isSuccessful) {
                            val firebaseUser : FirebaseUser = task.result!!.user!!
                            showErrorFunction("You are registered successfully. Your user id is ${firebaseUser.uid}",
                            false)
                            FirebaseAuth.getInstance().signOut()
                            finish()
                        } else {
                            showErrorFunction(task.exception!!.message.toString(), true)
                        }
                    }
                )
        }
    }

}