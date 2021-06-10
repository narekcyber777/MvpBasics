package com.narcyber.mvpbasics.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivityMainBinding;
import com.narcyber.mvpbasics.presenter.MainActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.utils.TextCustomizer;
import com.narcyber.mvpbasics.utils.Validator;

public class MainActivity extends AppCompatActivity implements TextCustomizer.TextCustomizerListener
        , MainActivityPresenter.MainActivityView {

    //const
    public static final String TAG = "MainActivity";
    final String TextSignInEventName = "SIGN_IN_EVENT_NAME";
    //root
    private ActivityMainBinding root;
    // other
    private TextWatcher emailTextWatcher, passwordTextWatcher;
    //vars
    private boolean isPasswordValid;
    private boolean isEmailValid;
    //presenter
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
            if (root.checkBox.isChecked()) {
                rememberPasswordAndLogin();
            }
            mainActivityPresenter.findUserByEmailAndPassword();
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
                if (Validator.isEmailValid(s.toString().trim())) {
                    root.emailLayout.setError(null);
                    isEmailValid = true;
                    mainActivityPresenter.setUserEmail(s.toString().trim());
                } else {
                    root.emailLayout.setError(getString(R.string.invalid_email));
                    root.emailLayout.requestFocus();
                }
                singInStatusCheck();
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
                if (Validator.isPasswordValid(s.toString().trim())) {
                    root.passLayout.setError(null);
                    isPasswordValid = true;
                    mainActivityPresenter.setUserPassword(s.toString().trim());
                } else {
                    root.passLayout.setError(getString(R.string.invalid_password));
                    root.passLayout.requestFocus();
                }
                singInStatusCheck();
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
        TextCustomizer textCustomizer = new TextCustomizer.Builder(root.requiredActivityTextView)
                .setCallBack(this)
                .setText(getString(R.string.go_sign_in))
                .addSpace(2)
                .push()
                .setText(getString(R.string.sign_up))
                .makeClickable(TextSignInEventName)
                .push()
                .build();
    }

    @Override
    public void onClick(String eventName) {
        switch (eventName) {
            case TextSignInEventName:
                MyUtils.moveTo(this, RegisterActivity.class);
                break;
        }
    }

    private void singInStatusCheck() {
        if (isEmailValid && isPasswordValid) {
            root.signIn.setEnabled(true);
            return;
        }
        root.signIn.setEnabled(false);
    }

    @Override
    public void ifExistGetKey(String key) {
        if (key == null) {
            MyUtils.showInToast(this, "Smth get Wrong");
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(MyUtils.KEY_ID, key);
            MyUtils.withArgumentsMoveTo(bundle, this, UserActivity.class);
            finish();
        }
    }

    @Override
    public void savedPasswordAndEmail(String email, String password) {
        root.email.setText(email);
        root.password.setText(password);
    }

    private void rememberPasswordAndLogin() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rememberPasswordAndLogin();
        mainActivityPresenter = null;
    }

}