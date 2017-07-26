package net.radityalabs.sampletravisci

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignupActivity : AppCompatActivity() {

    companion object {
        private val TAG = SignupActivity::class.java.simpleName
    }

    private lateinit var nameText: EditText
    private lateinit var addressText: EditText
    private lateinit var emailText: EditText
    private lateinit var mobileText: EditText
    private lateinit var passwordText: EditText
    private lateinit var reEnterPasswordText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginLink: TextView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setView()
    }

    private fun setView() {
        signupButton.setOnClickListener { signup() }
        loginLink.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    private fun initView() {
        signupButton = findViewById<Button>(R.id.btn_signup)
        loginLink = findViewById<TextView>(R.id.link_login)
        nameText = findViewById<EditText>(R.id.input_name)
        addressText = findViewById<EditText>(R.id.input_address)
        emailText = findViewById<EditText>(R.id.input_email)
        mobileText = findViewById<EditText>(R.id.input_mobile)
        passwordText = findViewById<EditText>(R.id.input_password)
        reEnterPasswordText = findViewById<EditText>(R.id.input_reEnterPassword)
    }

    fun signup() {
        Log.d(TAG, "Signup")
        if (!validate()) {
            onSignupFailed()
            return
        }

        signupButton.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        val name = nameText.text.toString()
        val address = addressText.text.toString()
        val email = emailText.text.toString()
        val mobile = mobileText.text.toString()
        val password = passwordText.text.toString()
        val reEnterPassword = reEnterPasswordText.text.toString()

        Handler().postDelayed({
            onSignupSuccess()
            progressDialog.dismiss()
        }, 3000)
    }


    fun onSignupSuccess() {
        signupButton.isEnabled = true
        setResult(Activity.RESULT_OK, null)
        finish()
    }

    fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        signupButton.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true
        val name = nameText.text.toString()
        val address = addressText.text.toString()
        val email = emailText.text.toString()
        val mobile = mobileText.text.toString()
        val password = passwordText.text.toString()
        val reEnterPassword = reEnterPasswordText.text.toString()

        if (name.isEmpty() || name.length < 3) {
            nameText.error = "at least 3 characters"
            valid = false
        } else {
            nameText.error = null
        }
        if (address.isEmpty()) {
            addressText.error = "Enter Valid Address"
            valid = false
        } else {
            addressText.error = null
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.error = "enter a valid email address"
            valid = false
        } else {
            emailText.error = null
        }

        if (mobile.isEmpty() || mobile.length != 10) {
            mobileText.error = "Enter Valid Mobile Number"
            valid = false
        } else {
            mobileText.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            passwordText.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            passwordText.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            reEnterPasswordText.error = "Password Do not match"
            valid = false
        } else {
            reEnterPasswordText.error = null
        }
        return valid
    }
}