package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management.HomePage;
import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management.MainActivity;
import com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UserManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.UMValidator;
import com.karawalaya.alliantbankapp.R;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutUserName;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextUserName;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;
    private AppCompatTextView textViewLinkForgotPassword;

    UserManagementDAO umdao = null;
    UMValidator umv = null;
//    DBHelper dbh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
//        login();
    }

/*    public void login() {
        login_BTN_Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean bool = umdao.checkUSer(login_ET_Email.getText().toString(), login_ET_Password.getText().toString());

                        if(bool)
                            Toast.makeText(Login.this, "Right", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Login.this, "Wrong", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }*/

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextUserName = (TextInputEditText) findViewById(R.id.textInputEditTextUserName);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
        textViewLinkForgotPassword = (AppCompatTextView) findViewById(R.id.textViewLinkForgotPassword);

    }

    private void initObjects() {
        umv = new UMValidator(this);
//        dbh = new DBHelper(this);
        umdao = new UserManagementDAO(this);
    }

    private void initListeners() {
/*        appCompatButtonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verifyFromSQLite();
                    }
                }
        );*/

/*        login_BTN_Register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                        startActivity(intentRegister);
                    }
                }
        );*/

        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        textViewLinkForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(intentRegister);
                break;
            case R.id.textViewLinkForgotPassword:
                Intent intentForgotPassword = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intentForgotPassword);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!umv.isInputEditTextFilled(textInputEditTextUserName, textInputLayoutUserName, getString(R.string.error_message_username_empty))) {
            return;
        }
        if (!umv.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password_empty))) {
            return;
        }

        Customer customer = umdao.login(textInputEditTextUserName.getText().toString(), textInputEditTextPassword.getText().toString());

        if(customer != null) {
            //Toast.makeText(this, "Welcome back, " + customer.getOnlineUser().getUserName() + "!", Toast.LENGTH_LONG).show();
            emptyInputEditText();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("customer", customer);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.error_message_login_not_valid), Toast.LENGTH_LONG).show();
            emptyInputEditText();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextUserName.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
