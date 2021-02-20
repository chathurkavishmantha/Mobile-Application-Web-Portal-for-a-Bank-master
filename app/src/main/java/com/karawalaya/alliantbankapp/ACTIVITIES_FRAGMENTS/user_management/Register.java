package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

;
import com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UserManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.OnlineUser;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.UMValidator;
import com.karawalaya.alliantbankapp.R;

/**
 * Created by delaroy on 3/27/17.
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutAccountNo;
    private TextInputLayout textInputLayoutUserName;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextAccountNo;
    private TextInputEditText textInputEditTextUserName;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    UserManagementDAO umdao = null;
    UMValidator umv = null;
    private OnlineUser onlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutAccountNo = (TextInputLayout) findViewById(R.id.textInputLayoutAccountNo);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextAccountNo = (TextInputEditText) findViewById(R.id.textInputEditTextAccountNo);
        textInputEditTextUserName = (TextInputEditText) findViewById(R.id.textInputEditTextUserName);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects() {
        umv = new UMValidator(this);
        umdao = new UserManagementDAO(this);
        onlineUser = new OnlineUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!umv.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email_empty))) {
            return;
        }
        if (!umv.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email_invalid))) {
            return;
        }
        if (!umv.isInputEditTextFilled(textInputEditTextAccountNo, textInputLayoutAccountNo, getString(R.string.error_message_account_no_empty))) {
            return;
        }
        if(!umv.isNumericOnly(textInputEditTextAccountNo, textInputLayoutAccountNo, getString(R.string.error_message_account_no_invalid))) {
            return;
        }
        if (!umv.isInputEditTextFilled(textInputEditTextUserName, textInputLayoutUserName, getString(R.string.error_message_username_empty))) {
            return;
        }
        if (!umv.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password_empty))) {
            return;
        }
        if (!umv.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_message_confirm_password_not_matching))) {
            return;
        }
        String customerId = umdao.seeWhetherPhysicallyRegistered(textInputEditTextEmail.getText().toString().trim(), textInputEditTextAccountNo.getText().toString());
        if(customerId != null){
            if(!umdao.alreadyAnOnlineUser(customerId)) {
                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setCustomerId(customerId);
                onlineUser.setUserName(textInputEditTextUserName.getText().toString());
                onlineUser.setPassword(textInputEditTextPassword.getText().toString());
                if (umdao.registerOnlineUser(onlineUser)) {
                    Toast.makeText(this, getString(R.string.success_message_registered), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                    finish();////===================================================
                } else {
                    Toast.makeText(this, "Sorry, some error!", Toast.LENGTH_LONG).show();
                    emptyInputEditText();
                }
            } else {
                Toast.makeText(this, getString(R.string.error_message_already_a_online_user), Toast.LENGTH_LONG).show();
                emptyInputEditText();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();///////////============================================================
            }
        } else {
            Toast.makeText(this, getString(R.string.error_message_no_physical_account), Toast.LENGTH_LONG).show();
            emptyInputEditText();
        }

/*        if (!umdao.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            boolean bool = umdao.addUser(user);

            if(bool) {
                // Snack Bar to show success message that record saved successfully
                Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                emptyInputEditText();

            } else {
                // Snack Bar to show success message that record saved successfully
                Snackbar.make(nestedScrollView, "NOPE", Snackbar.LENGTH_LONG).show();
                emptyInputEditText();
            }


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }*/


    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextAccountNo.setText(null);
        textInputEditTextUserName.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}