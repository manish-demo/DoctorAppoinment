package com.doctorappoinmentdemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.activity.LoginActivity;
import com.doctorappoinmentdemo.utils.UserSession;

import static com.doctorappoinmentdemo.utils.UserSession.PREFER_NAME;


public class UserProfileFragment extends Fragment  {

    TextView tvUserName,tvUserEmail,tvUserMobile;
    Button btnLogout;
    UserSession session;
    SharedPreferences sharedPreferences;
    String strName,StrEmail,StrPhone;

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_profile, viewGroup, false);
        tvUserName = rootview.findViewById(R.id.etUserName);
        tvUserEmail = rootview.findViewById(R.id.etEmail);
        tvUserMobile = rootview.findViewById(R.id.etMobile);
        btnLogout = rootview.findViewById(R.id.btnLogout);
        init();
        return rootview;
    }
    private void init(){
        session = new UserSession(getActivity());
        sharedPreferences = getActivity().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.contains("Email"))
        {
            StrEmail = sharedPreferences.getString("Email", "");
        }
        if (sharedPreferences.contains("mobile"))
        {
            StrPhone = sharedPreferences.getString("mobile", "");
        }
        if (sharedPreferences.contains("Name")){
            strName = sharedPreferences.getString("Name","");
        }
//        tvUserMobile.setText(StrPhone);
        tvUserName.setText(strName);
        tvUserEmail.setText(StrEmail);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageOKCancel("Confirmation !\nAre you sure your want to logout ?\nYou need to sign up again if your logout",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                session.logoutUser();
                                dialog.dismiss();
                            }
                        });
            }
        });
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
