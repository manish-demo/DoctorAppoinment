package com.doctorappoinmentdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doctorappoinmentdemo.MainActivity;
import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.utils.AppAlerts;
import com.doctorappoinmentdemo.utils.AppProgressDialog;
import com.doctorappoinmentdemo.utils.UserSession;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREFER_NAME = "Reg";

    Button buttonLogin;

    EditText txtEmail, txtPassword;
    String strEmail,strPassword;
    AppAlerts appAlerts;
    TextView tvSignUp;
    AppProgressDialog appProgressDialog;
    // User Session Manager Class
    UserSession session;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new UserSession(getApplicationContext());
        if (session.isUserLoggedIn()){
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }else {

        }
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        init();
    }

    private void init(){
        findIds();
        // User Session Manager
        session = new UserSession(getApplicationContext());
        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
    }
    private void clickListner(){
        tvSignUp.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        appAlerts = new AppAlerts();
        appProgressDialog = new AppProgressDialog(this);
    }

    private boolean isValidInput(View view){
        if (strEmail.isEmpty()&&strPassword.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter all the details");
            return false;
        }else if (strEmail.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your email address");
            return false;
        }else if (strPassword.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your password");
            return false;
        }else{
            return true;
        }
    }

    private void getUserInput(){
        strEmail = txtEmail.getText().toString().trim();
        strPassword = txtPassword.getText().toString().trim();
    }

    public void findIds(){
        txtEmail = findViewById(R.id.etMobile);
        txtPassword = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvsignUp);
        clickListner();
    }
    private void doUserLogin(View view){
         String uPassword = "";
         String uEmail = "";
         String uName = "";
         String uMobile = "";
        appProgressDialog.show();
        if (sharedPreferences.contains("Email"))
        {
            uEmail = sharedPreferences.getString("Email", "");
        }
        if (sharedPreferences.contains("txtPassword"))
        {
            uPassword = sharedPreferences.getString("txtPassword", "");
        }
        if (sharedPreferences.contains("mobile")){
            uMobile = sharedPreferences.getString("mobile", "");
        }
        if (sharedPreferences.contains("Name")){
            uName = sharedPreferences.getString("Name", "");
        }

        if (uEmail.equalsIgnoreCase(strEmail) && uPassword.equalsIgnoreCase(strPassword)){
            session.createUserLoginSession(uEmail,
                    uPassword,uName,uMobile);
            Intent i = new  Intent(getApplicationContext(), HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            appProgressDialog.hide();
        }else {
            appProgressDialog.hide();
            appAlerts.showSnackBar(view,"Incorrect Email or Password");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                getUserInput();
                if (isValidInput(v)){
                    doUserLogin(v);
                }
                break;
            case R.id.tvsignUp:
                Intent intent = new Intent(this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}