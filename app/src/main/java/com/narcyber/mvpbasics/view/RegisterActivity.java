package com.narcyber.mvpbasics.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivitySignUpBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.RegisterActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.utils.TextCustomizer;
import com.narcyber.mvpbasics.utils.Validator;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityPresenter.RegisterView {


    private RegisterActivityPresenter registerActivityPresenter;
    private TextWatcher emailTextWatcher, passwordTextWatcher, userNameWatcher, nameWatcher;
    private ActivitySignUpBinding root;
    private boolean isTermsErrorShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        customizeWidgets();
        inIt();
    }

    private void inIt() {
        registerActivityPresenter = new RegisterActivityPresenter(this, this);
        root.signUp.setEnabled(true);
        root.signUp.setOnClickListener(v -> {
            if (!validateAndSend()) {
                if (isTermsErrorShown) {
                    MyUtils.showInToast(RegisterActivity.this, getString(R.string.accept_terms_please));
                } else {
                    MyUtils.showInToast(RegisterActivity.this, getString(R.string.error_sign_up));
                }
            }
        });

        emailTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    root.emailLayout.setError(null);
                } else if (Validator.isEmailValid(s.toString().trim())) {
                    registerActivityPresenter.isEmailTaken(s.toString().trim());// if very
                } else {
                    root.emailLayout.setError(getString(R.string.invalid_email));
                    root.emailLayout.requestFocus();
                }
            }
        };
        passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    root.passLayout.setError(null);
                } else if (Validator.isPasswordValid(s.toString().trim())) {
                    root.passLayout.setError(null);

                } else {
                    root.passLayout.setError(getString(R.string.invalid_password));
                    root.passLayout.requestFocus();

                }
            }
        };

        userNameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    root.usernameLayout.setError(null);
                } else if (Validator.isUsernameValid(s.toString().trim())) {
                    registerActivityPresenter.isUsernameTaken(s.toString());

                } else {
                    root.usernameLayout.setError(getString(R.string.invalid_username));
                    root.usernameLayout.requestFocus();
                }
            }
        };
        nameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    root.fullNameLayout.setError(null);
                } else if (Validator.isFullNameValid(s.toString().trim())) {
                    root.fullNameLayout.setError(null);
                } else {
                    root.fullNameLayout.setError(getString(R.string.invalid_full_name));
                    root.fullNameLayout.requestFocus();
                }
            }
        };
    }

    private boolean validateAndSend() {
        String email, password, userName, fullName;
        email = password = userName = fullName = null;
        if (root.email.getText() == null || root.email.getText().toString().isEmpty()) {
            root.emailLayout.setError(getString(R.string.error_empty));
        } else {
            email = root.email.getText().toString();
        }
        if (root.fullName.getText() == null || root.fullName.getText().toString().isEmpty()) {
            root.fullNameLayout.setError(getString(R.string.error_empty));
        } else {
            fullName = root.fullName.getText().toString();
        }
        if (root.password.getText() == null || root.password.getText().toString().isEmpty()) {
            root.passLayout.setError(getString(R.string.error_empty));
        } else {
            password = root.password.getText().toString();
        }
        if (root.username.getText() == null || root.username.getText().toString().isEmpty()) {
            root.usernameLayout.setError(getString(R.string.error_empty));
        } else {
            userName = root.username.getText().toString();
        }
        if (!singUpStatusCheck()) return false;
        registerActivityPresenter.pushUserIntoDb(email, fullName, password, userName);
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerListeners();
    }

    private void registerListeners() {
        root.email.addTextChangedListener(emailTextWatcher);
        root.fullName.addTextChangedListener(nameWatcher);
        root.password.addTextChangedListener(passwordTextWatcher);
        root.username.addTextChangedListener(userNameWatcher);
    }

    private void removeListeners() {
        root.email.removeTextChangedListener(emailTextWatcher);
        root.fullName.removeTextChangedListener(nameWatcher);
        root.password.removeTextChangedListener(passwordTextWatcher);
        root.username.removeTextChangedListener(userNameWatcher);
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeListeners();
    }

    private void customizeWidgets() {
        root.signUp.setEnabled(false);
        new TextCustomizer.Builder(root.requiredActivityTextView)
                .setCallBack(eventName -> {
                    switch (eventName) {
                        case ConstantHelper.TextSignUpEventName: {
                            MyUtils.moveToAndClear(this, MainActivity.class);
                        }
                    }
                })
                .setText(getString(R.string.suggest_register_text))
                .addSpace(2)
                .push()
                .setText(getString(R.string.sign_in))
                .makeClickable(ConstantHelper.TextSignUpEventName, getResources().getColor(R.color.blue))
                .push()
                .build();
        new TextCustomizer.Builder(root.checkBox)
                .setCallBack(eventName -> {
                })
                .setText(getString(R.string.accept_terms))
                .isBold(true)
                .push()
                .setText(getString(R.string.terms_conditions_string))
                .makeClickable(ConstantHelper.TextTermsEventName, getResources().getColor(R.color.blue))
                .push()
                .build();
    }

    private boolean singUpStatusCheck() {
        if (isNull(root.emailLayout.getError()) && isNull(root.passLayout.getError())
                && isNull(root.fullNameLayout.getError()) && isNull(root.usernameLayout.getError())) {
            if (!root.checkBox.isChecked()) {
                isTermsErrorShown = true;
                return false;
            }
            return true;
        }
        isTermsErrorShown = false;
        return false;
    }

    @Override
    public boolean isEmailUsed(boolean isUsed) {
        if (!isUsed) {
            root.emailLayout.setError(null);
        } else {
            root.emailLayout.setError(getString(R.string.email_used));
        }
        return false;
    }

    @Override
    public boolean isUsernameUsed(boolean isUsed) {
        if (!isUsed) {
            root.usernameLayout.setError(null);
        } else {
            root.usernameLayout.setError(getString(R.string.email_used));
        }
        return false;
    }

    @Override
    public boolean notifyUserSuccessRegistered() {

        MyUtils.showInToast(RegisterActivity.this, getString(R.string.success_sign_up));
        MyUtils.moveToAndClear(RegisterActivity.this, MainActivity.class);
        return false;
    }

    @Override
    public boolean notifyUserRegFailed() {
        MyUtils.showInToast(this, getString(R.string.error_sign_in));
        return false;
    }

    public boolean isNull(Object object) {
        return object == null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerActivityPresenter = null;
    }
}
