package com.doctorappoinmentdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.utils.AppAlerts;
import com.doctorappoinmentdemo.utils.AppProgressDialog;
import com.doctorappoinmentdemo.utils.UserSession;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button buttonRegister;
    EditText txtUsername, txtPassword, txtEmail,txtMobile;
    String strEmail,strUserName,strUserPhone,strUserPassword;
    UserSession session;
    AppProgressDialog appProgressDialog;
    AppAlerts appAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().hide();
        init();
    }

    private void init(){
        findIds();
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        // get editor to edit in file

        appAlerts = new  AppAlerts();
        appProgressDialog = new AppProgressDialog(this);
        buttonRegister.setOnClickListener(this);
    }

    private void findIds(){
        txtUsername = (EditText) findViewById(R.id.etUserName);
        txtPassword = (EditText) findViewById(R.id.etPassword);
        txtEmail = (EditText) findViewById(R.id.etEmail);
        txtMobile = findViewById(R.id.etMobile);
        buttonRegister = (Button) findViewById(R.id.btnRegister);
    }

    private boolean isValidInput(View view){
        if (strEmail.isEmpty()&&strUserName.isEmpty()&&strUserPhone.isEmpty()&&strUserPassword.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter all the details");
            return false;
        }else if (strEmail.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your email address");
            return false;
        }else if (strUserPassword.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your password");
            return false;
        }else if (strUserName.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your Name");
            return false;
        }else if (strUserPhone.isEmpty()){
            appAlerts.showSnackBar(view,"Please enter your mobile no");
            return false;
        }else{
            return true;
        }
    }

    private void getUserInput(){

        strEmail = txtEmail.getText().toString().trim();
        strUserName = txtUsername.getText().toString().trim();
        strUserPassword = txtPassword.getText().toString().trim();
        strUserPhone = txtMobile.getText().toString().trim();
    }

    private void doUserRegistration(){
        appProgressDialog.show();
        // as now we have information in string. Lets stored them with the help of editor
        editor = sharedPreferences.edit();
        editor.putString("Name", strUserName);
        editor.putString("Email",strEmail);
        editor.putString("txtPassword",strUserPassword);
        editor.putString("mobile",strUserPhone);
        editor.apply();
            appProgressDialog.hide();
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
    }   // commit the values


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                getUserInput();
            if (isValidInput(v)){
               doUserRegistration();
            }
        }
    }
}