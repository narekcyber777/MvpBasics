package com.narcyber.mvpbasics.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityMainBinding;
import com.narcyber.mvpbasics.helper.ConstantHelper;
import com.narcyber.mvpbasics.presenter.MainActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.utils.TextCustomizer;
import com.narcyber.mvpbasics.utils.Validator;

public class MainActivity extends AppCompatActivity implements
        MainActivityPresenter.MainActivityView {

    private ActivityMainBinding root;
    private TextWatcher emailTextWatcher, passwordTextWatcher;
    private MainActivityPresenter mainActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        customizeWidgets();
        inIt();
    }

    private void inIt() {
        mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        mainActivityPresenter.sendPasswordAndEmailLastRegistered();
        root.signIn.setOnClickListener(v -> {
            if (!validateAndFind()) {
                removeAllSavedObjects();
                MyUtils.showInToast(MainActivity.this, getString(R.string.error_sign_up));
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
                    root.emailLayout.setError(null);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerListeners();
    }

    private void registerListeners() {
        root.email.addTextChangedListener(emailTextWatcher);
        root.password.addTextChangedListener(passwordTextWatcher);
    }

    private void unregisterListeners() {
        root.email.removeTextChangedListener(emailTextWatcher);
        root.password.removeTextChangedListener(passwordTextWatcher);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterListeners();
    }

    private void customizeWidgets() {
        new TextCustomizer.Builder(root.requiredActivityTextView)
                .setCallBack(eventName -> {
                    switch (eventName) {
                        case ConstantHelper.TextSignInEventName:
                            removeAllSavedObjects();
                            MyUtils.moveTo(this, RegisterActivity.class);
                            break;
                    }
                })
                .setText(getString(R.string.go_sign_in))
                .addSpace(2)
                .push()
                .setText(getString(R.string.sign_up))
                .makeClickable(ConstantHelper.TextSignInEventName, getResources().getColor(R.color.blue))
                .push()
                .build();
    }

    private void removeAllSavedObjects() {
        mainActivityPresenter.removeLocal();
    }

    private boolean singInStatusCheck() {
        return root.passLayout.getError() == null && root.emailLayout.getError() == null;
    }

    @Override
    public void ifExistGetKey(String key) {
        if (key == null) {

            root.emailLayout.setError(getString(R.string.user_not_found));
            root.passLayout.setError(getString(R.string.user_not_found));
            MyUtils.showInToast(this, getString(R.string.error_sign_in));
        } else {
            rememberPasswordAndLoginIfNeed();
            Bundle bundle = new Bundle();
            bundle.putString(ConstantHelper.KEY_ID, key);
            MyUtils.withArgumentsMoveToAndClear(bundle, this, UserActivity.class);
        }
    }

    @Override
    public void savedPasswordAndEmail(String email, String password) {
        mainActivityPresenter.findUserByEmailAndPassword(email, password);
    }

    private void rememberPasswordAndLoginIfNeed() {
        if (!root.checkBox.isChecked()) {
            mainActivityPresenter.removeLocal();
        } else {
            boolean b = root.email.getText() != null && !root.email.getText().toString().isEmpty()
                    && root.password.getText() != null
                    && !root.password.getText().toString().isEmpty();
            if (b) mainActivityPresenter.rememberPasswordAndEmail(root.email.getText().toString(),
                    root.password.getText().toString());
        }
    }

    private boolean validateAndFind() {
        String email, password;
        email = password = null;
        if (root.email.getText() == null || root.email.getText().toString().isEmpty()) {
            root.emailLayout.setError(getString(R.string.error_empty));
        } else {
            email = root.email.getText().toString();
        }
        if (root.password.getText() == null || root.password.getText().toString().isEmpty()) {
            root.passLayout.setError(getString(R.string.error_empty));
        } else {
            password = root.password.getText().toString();
        }
        if (!singInStatusCheck()) return false;
        mainActivityPresenter.findUserByEmailAndPassword(email, password);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter = null;
    }

}