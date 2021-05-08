package com.doctorappoinmentdemo.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class AppAlerts {

    public void showSnackBar(View container,String msg){
            Snackbar.make(container, msg, Snackbar.LENGTH_LONG).show();
    }
}
