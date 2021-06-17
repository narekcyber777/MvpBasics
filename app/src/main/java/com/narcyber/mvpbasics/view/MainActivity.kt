package com.narcyber.mvpbasics.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.databinding.ActivityMainBinding
import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.helper.DataSaveHelper
import com.narcyber.mvpbasics.model.User
import com.narcyber.mvpbasics.presenter.MainActivityPresenter
import com.narcyber.mvpbasics.utils.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.MainActivityView {
    private lateinit var root: ActivityMainBinding
    private lateinit var emailTextWatcher: TextWatcher
    private lateinit var passwordTextWatcher: TextWatcher
    private lateinit var textManipulator: TextManipulator
    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = ActivityMainBinding.inflate(layoutInflater)
        setContentView(root.root)
        customizeWidgets()
        inIt()
    }

    override fun onStart() {
        super.onStart()
        registerListeners()
    }

    private fun customizeWidgets() {
        textManipulator = TextManipulator(root.requiredActivityTextView).also {
            it.with(object : TextManipulator.TextManipulatorCallBack {
                override fun onEvent(event: String) {
                    when (event) {
                        ConstantHelper.TextSignInEventName -> {
                            moveTo(this@MainActivity, RegisterActivity::class.java)
                        }
                    }
                }
            })
            it.addText(getString(R.string.go_sign_in))
            it.addSpace(2)
            it.push()
            it.addText(getString(R.string.sign_up))
            it.makeClickable(ConstantHelper.TextSignInEventName, TextManipulator.Params().apply {
                this.colorText = getColor(R.color.blue)
            })
            it.push()

        }

    }


    private fun inIt() {
        mainActivityPresenter = MainActivityPresenter(
            this,
            DataSaveHelper(this)
        ).apply {
            this.sendPasswordAndEmailLastRegistered()
        }
        root.signIn.setOnClickListener {
            if (!validateAndFind()) {
                removeAllSavedObjects()
                showInToast(this, getString(R.string.sign_in))
            }
        }
        emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {
                    root.emailLayout.error = null
                } else if (Validator.isEmailValid(s.toString().trim())) {
                    root.emailLayout.error = null
                } else {
                    root.emailLayout.error = getString(R.string.invalid_email)
                    root.emailLayout.requestFocus()
                }
            }
        }
        passwordTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when {
                    s.toString().isEmpty() -> {
                        root.passLayout.error = null
                    }
                    Validator.isPasswordValid(s.toString().trim()) -> {
                        root.passLayout.error = null
                    }
                    else -> {
                        root.passLayout.error = getString(R.string.invalid_password)
                        root.passLayout.requestFocus()
                    }
                }
            }
        }
    }

    private fun validateAndFind(): Boolean {
        var email: String = ""
        var password: String = ""

        if (root.email.text == null || root.email.text.toString().isEmpty()) {
            root.emailLayout.error = getString(R.string.error_empty)
        } else {
            email = root.email.text.toString()
        }
        if (root.password.text == null || root.password.text.toString().isEmpty()) {
            root.passLayout.error = getString(R.string.error_empty)
        } else {
            password = root.password.text.toString()
        }
        if (!singInStatusCheck()) return false
        mainActivityPresenter.findUserByEmailAndPassword(email, password)
        return true
    }

    private fun singInStatusCheck(): Boolean =
        root.passLayout.error == null && root.emailLayout.error == null

    private fun removeAllSavedObjects() {
        mainActivityPresenter.removeLocal()
    }

    override fun savedPasswordAndEmail(email: String?, password: String?) {
        root.email.setText(email)
        root.password.setText(password)
        root.checkBox.isChecked = true
    }

    open fun rememberPasswordAndLoginIfNeed() {
        if (!root.checkBox.isChecked) {
            mainActivityPresenter.removeLocal()
            return
        }
        val b = (root.email.text != null && !root.email.text.toString().isEmpty()
                && root.password.text != null && !root.password.text.toString().isEmpty())
        if (b) mainActivityPresenter.rememberPasswordAndEmail(
            root.email.text.toString(),
            root.password.text.toString()
        )
    }

    override fun ifExistGetUsername(user: User?) {
        if (user == null) {
            root.emailLayout.error = getString(R.string.user_not_found)
            root.passLayout.error = getString(R.string.user_not_found)
            showInToast(this, getString(R.string.error_sign_in))
        } else {
            rememberPasswordAndLoginIfNeed()
            val bundle = Bundle()
            bundle.putSerializable(getString(R.string.key_id), user)
            withArgumentsMoveToAndClear(bundle, this, HomeActivity::class.java)
        }
    }

    private fun registerListeners() {
        root.email.addTextChangedListener(emailTextWatcher)
        root.password.addTextChangedListener(passwordTextWatcher)
    }

    private fun unregisterListeners() {
        root.email.removeTextChangedListener(emailTextWatcher)
        root.password.removeTextChangedListener(passwordTextWatcher)
    }

    override fun onStop() {
        super.onStop()
        unregisterListeners()
    }
}

