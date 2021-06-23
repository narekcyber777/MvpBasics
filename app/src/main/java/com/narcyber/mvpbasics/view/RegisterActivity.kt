package com.narcyber.mvpbasics.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.databinding.ActivitySignUpBinding
import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.presenter.RegisterActivityPresenter
import com.narcyber.mvpbasics.utils.*

class RegisterActivity : AppCompatActivity(), RegisterActivityPresenter.RegisterView {
    private lateinit var root: ActivitySignUpBinding
    private lateinit var emailTextWatcher: TextWatcher
    private lateinit var passwordTextWatcher: TextWatcher
    private lateinit var userNameWatcher: TextWatcher
    private lateinit var nameWatcher: TextWatcher
    private lateinit var textManipulator: TextManipulator
    private lateinit var textManipulator2: TextManipulator
    private lateinit var registerActivityPresenter: RegisterActivityPresenter
    private var isTermsErrorShown: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(root.root)
        customizeWidgets()
        inIt()
    }

    private fun customizeWidgets() {
        textManipulator = TextManipulator(root.requiredActivityTextView).also {
            it.with(object : TextManipulator.TextManipulatorCallBack {
                override fun onEvent(event: String) {
                    when (event) {
                        ConstantHelper.TextSignUpEventName -> {
                            moveTo(this@RegisterActivity, MainActivity::class.java)
                        }
                    }
                }
            })
            it.addText(getString(R.string.suggest_register_text))
            it.addSpace(2)
            it.push()
            it.addText(getString(R.string.sign_in))
            it.makeClickable(
                ConstantHelper.TextSignUpEventName, TextManipulator.Params()
            )
            it.push()
        }
        textManipulator2 = TextManipulator(root.checkBox).also {
            it.with(object : TextManipulator.TextManipulatorCallBack {
                override fun onEvent(event: String) {
                }
            })
            it.addSpace(2)
            it.addText(getString(R.string.terms_conditions_string))
            it.makeClickable(ConstantHelper.TextSignUpEventName, TextManipulator.Params())
            it.push()
        }
    }

    private fun inIt() {
        registerActivityPresenter = RegisterActivityPresenter(this)
        root.signUp.setOnClickListener { v ->
            if (!validateAndSend()) {
                if (isTermsErrorShown) {
                    showInToast(
                        this@RegisterActivity,
                        getString(R.string.accept_terms_please)
                    )
                } else {
                    showInToast(this@RegisterActivity, getString(R.string.error_sign_up))
                }
            }
        }


        emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                when {
                    s.toString().isEmpty() -> {
                        root.emailLayout.error = null
                    }
                    Validator.isEmailValid(s.toString().trim { it <= ' ' }) -> {
                        registerActivityPresenter.isEmailTaken(
                            s.toString().trim { it <= ' ' }) // if very
                    }
                    else -> {
                        root.emailLayout.error = getString(R.string.invalid_email)
                        root.emailLayout.requestFocus()
                    }
                }
            }
        }
        passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) {
                    root.passLayout.error = null
                } else if (Validator.isPasswordValid(s.toString().trim { it <= ' ' })) {
                    root.passLayout.error = null
                } else {
                    root.passLayout.error = getString(R.string.invalid_password)
                    root.passLayout.requestFocus()
                }
            }
        }

        userNameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                when {
                    s.toString().isEmpty() -> {
                        root.usernameLayout.error = null
                    }
                    Validator.isUsernameValid(s.toString().trim { it <= ' ' }) -> {
                        registerActivityPresenter.isUserNameTaken(s.toString().trim { it <= ' ' })
                    }
                    else -> {
                        root.usernameLayout.error = getString(R.string.invalid_username)
                        root.usernameLayout.requestFocus()
                    }
                }
            }
        }
        nameWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                when {
                    s.toString().isEmpty() -> {
                        root.fullNameLayout.error = null
                    }
                    Validator.isFullNameValid(s.toString().trim { it <= ' ' }) -> {
                        root.fullNameLayout.error = null
                    }
                    else -> {
                        root.fullNameLayout.error = getString(R.string.invalid_full_name)
                        root.fullNameLayout.requestFocus()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        removeListeners()
    }

    private fun validateAndSend(): Boolean {
        var email: String = ""
        var password: String = ""
        var userName: String = ""
        var fullName: String = ""

        if (isBlankIn(root.email)) {
            root.emailLayout.error = getString(R.string.error_empty)
        } else {
            email = root.email.text.toString()
        }
        if (isBlankIn(root.fullName)) {
            root.fullNameLayout.error = getString(R.string.error_empty)
        } else {
            fullName = root.fullName.text.toString()
        }
        if (isBlankIn(root.password)) {
            root.passLayout.error = getString(R.string.error_empty)
        } else {
            password = root.password.text.toString()
        }
        if (isBlankIn(root.username)) {
            root.usernameLayout.error = getString(R.string.error_empty)
        } else {
            userName = root.username.text.toString()
        }
        if (!singUpStatusCheck()) return false
        registerActivityPresenter.pushUserIntoDB(email, userName, fullName, password)
        return true
    }


    private fun singUpStatusCheck(): Boolean {
        if (isNull(root.emailLayout.error) && isNull(root.passLayout.error)
            && isNull(root.fullNameLayout.error) && isNull(root.usernameLayout.error)
        ) {
            if (!root.checkBox.isChecked) {
                isTermsErrorShown = true
                return false
            }
            return true
        }
        isTermsErrorShown = false
        return false
    }

    override fun isEmailUsed(isUsed: Boolean): Boolean {
        if (!isUsed) root.emailLayout.error = null
        else root.emailLayout.error = getString(R.string.email_used)
        return false
    }

    override fun isUsernameUsed(isUsed: Boolean): Boolean {
        if (!isUsed) root.usernameLayout.error = null
        else root.usernameLayout.error = getString(R.string.email_used)

        return false
    }

    override fun notifyUserSuccessRegistered(): Boolean {
        showInToast(this, getString(R.string.success_sign_up))
        moveToAndClear(this@RegisterActivity, MainActivity::class.java)
        return true
    }

    override fun notifyUserRegFailed(): Boolean {
        showInToast(this, getString(R.string.error_sign_up))
        return true
    }

    private fun registerListeners() {
        root.email.addTextChangedListener(emailTextWatcher)
        root.fullName.addTextChangedListener(nameWatcher)
        root.password.addTextChangedListener(passwordTextWatcher)
        root.username.addTextChangedListener(userNameWatcher)
    }

    private fun removeListeners() {
        root.email.removeTextChangedListener(emailTextWatcher)
        root.fullName.removeTextChangedListener(nameWatcher)
        root.password.removeTextChangedListener(passwordTextWatcher)
        root.username.removeTextChangedListener(userNameWatcher)
    }


}