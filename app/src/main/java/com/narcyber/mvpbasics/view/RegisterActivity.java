package com.narcyber.mvpbasics.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.narcyber.mvpbasics.R;
import com.narcyber.mvpbasics.databinding.ActivitySignUpBinding;
import com.narcyber.mvpbasics.model.User;
import com.narcyber.mvpbasics.presenter.RegisterActivityPresenter;
import com.narcyber.mvpbasics.utils.MyUtils;
import com.narcyber.mvpbasics.utils.TextCustomizer;
import com.narcyber.mvpbasics.utils.Validator;

import java.lang.ref.WeakReference;

public class RegisterActivity extends AppCompatActivity  implements TextCustomizer.TextCustomizerListener
                                                                 , RegisterActivityPresenter.RegisterView {




    //const
    public  static  final  String TAG="MainActivity";
    final  String TextSignUpEventName="SIGN_UP_EVENT_NAME";
    final  String TextTermsEventName="TERMS_EVENT_CONDITIONS";

    //presenter
   private RegisterActivityPresenter registerActivityPresenter;

    //other
    private  TextWatcher emailTextWatcher,passwordTextWatcher,userNameWatcher,nameWatcher;

    //vars
    private  boolean isUsernameValid,isPasswordValid,isEmailValid,isNameValid;

    //root
    private ActivitySignUpBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());
        customizeWidgets();
        inIt();

      ;

    }

 


    private void    inIt() {




        registerActivityPresenter=new RegisterActivityPresenter(this,getApplicationContext());
            root.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

                    singUpStatusCheck();


            });
        root.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerActivityPresenter.pushUserIntoDb();

                MyUtils.showInToast(getApplicationContext(),"Successfuly Sign Up");
                MyUtils.moveToAndClear(RegisterActivity.this,MainActivity.class);
                finish();

            }
        });






        

        emailTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Validator.isEmailValid(s.toString().trim())){

                    //if everything is okey go to second level

                    Log.d("Nar",(registerActivityPresenter==null)+" isNull "+s.toString());


                    registerActivityPresenter.setUserEmail(s.toString());
                    registerActivityPresenter.isEmailTaken(s.toString().trim());// if very

                    
                    

                }else{

                    root.emailLayout.setError(getString(R.string.invalid_email));
                    root.emailLayout.requestFocus();
                    isEmailValid=false;


                }
                singUpStatusCheck();

            }

        };
        passwordTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // aaA@78Z%c
                Log.d(TAG,s.toString().trim()+" s "+s.length());

                if(Validator.isPasswordValid(s.toString().trim())){
                    root.passLayout.setError(null);
                    isPasswordValid=true;

                    registerActivityPresenter.setUserPassword(s.toString().trim());

                }else{
                    root.passLayout.setError(getString(R.string.invalid_password));
                    root.passLayout.requestFocus();
                    isPasswordValid=false;


                }
                singUpStatusCheck();

            }
        };

        userNameWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Validator.isUsernameValid(s.toString().trim())){
                  registerActivityPresenter.isUsernameTaken(s.toString());
                  registerActivityPresenter.setUsersUserName(s.toString().trim());


                }else{
                    root.usernameLayout.setError(getString(R.string.invalid_username));
                    root.usernameLayout.requestFocus();
                    isUsernameValid=false;


                }
                singUpStatusCheck();
            }
        };

        nameWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(Validator.isFullNameValid(s.toString().trim())){
                    root.fullNameLayout.setError(null);
                    isNameValid=true;

                    registerActivityPresenter.setUserFullName(s.toString().trim());

                }else{
                    root.fullNameLayout.setError(getString(R.string.invalid_full_name));
                    root.fullNameLayout.requestFocus();
                    isNameValid=false;
                }


                singUpStatusCheck();
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
        root.fullName.addTextChangedListener(nameWatcher);
        root.password.addTextChangedListener(passwordTextWatcher);
        root.username.addTextChangedListener(userNameWatcher);
    }
    private  void removeListeners(){
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
        TextCustomizer textCustomizer= new TextCustomizer.Builder(root.requiredActivityTextView)
                .setCallBack(this)
                .setText(getString(R.string.suggest_register_text))
                .addSpace(2)
                .push()
                .setText(getString(R.string.sign_in))
                .makeClickable(TextSignUpEventName)
                .push()
                .build();
        TextCustomizer textCustomizer2=new TextCustomizer.Builder(root.checkBox)
                .setCallBack(this)
                .setText(getString(R.string.accept_terms))
                .isBold(true)
                .push()
                .setText(getString(R.string.terms_conditions_string))
                .makeClickable(TextTermsEventName)
                .push()
                .build();


    }


    @Override
    public void onClick(String eventName) {
        switch (eventName) {
            case TextSignUpEventName:{
                MyUtils.moveToAndClear(this,MainActivity.class);
            }
        }

    }
    private  void singUpStatusCheck(){
        if(isEmailValid && isPasswordValid && isNameValid && isUsernameValid){

             if(!root.checkBox.isChecked()){

                 root.signUp.setEnabled(false);
                 MyUtils.showInToast(RegisterActivity.this,"Please Accept our  Terms and Conditions");
                 return;
             }
            root.signUp.setEnabled(true);


            return;
        }
        root.signUp.setEnabled(false);
    }

    @Override
    public boolean isEmailUsed(boolean isUsed) {
        
        if(!isUsed) {
            root.emailLayout.setError(null);

            isEmailValid = true;
        }else{
            root.emailLayout.setError(getString(R.string.email_used));
            isEmailValid=false;
        }
        
        return false;
    }

    @Override
    public boolean isUsernameUsed(boolean isUsed) {
        if(!isUsed) {
            root.usernameLayout.setError(null);

            isUsernameValid = true;


        }else{
            root.usernameLayout.setError(getString(R.string.email_used));
            isUsernameValid=false;
        }

        return false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerActivityPresenter=null;
        Log.d("Nar","destroyed");
    }
}
