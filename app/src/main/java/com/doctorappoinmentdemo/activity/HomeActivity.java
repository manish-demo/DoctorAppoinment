package com.doctorappoinmentdemo.activity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.fragment.DoctorsListFragment;
import com.doctorappoinmentdemo.fragment.UserProfileFragment;

import java.util.ArrayList;
import java.util.Arrays;

import static com.doctorappoinmentdemo.R.id.fragmentContainer;
import static com.doctorappoinmentdemo.R.id.llBooking;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spinner;
    ImageView ivBooking,ivProfile;
    LinearLayout llNooking,llProfile;
    String city = "";

    private String[] categories = {
            "All",
            "Banner",
            "Kothurd",
    };
    private String[] cities = {
            "Pune",
            "Indore",
            "Mumbai",
            "Ujjain",
            "Udaipur",
            "Jaipur"
    };

    ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(cities));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getSupportActionBar().hide();
        init();
    }

    private void init(){
        addFragment(fragmentContainer,   DoctorsListFragment.newInstance("Pune"),"");
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinnertype, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        ivBooking = findViewById(R.id.ivBooking);
        ivProfile = findViewById(R.id.ivProfile);
        llNooking = findViewById(llBooking);
        llProfile = findViewById(R.id.llProfile);
        llProfile.setOnClickListener(this);
        llNooking.setOnClickListener(this);
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }
    protected void replaceFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llBooking:
                ivBooking.setColorFilter(R.color.blue);
                ivProfile.setColorFilter(R.color.dark_gray);
                city = "Pune";
                spinner.setSelection(0);
                addFragment(fragmentContainer,   DoctorsListFragment.newInstance(city),"");
                break;
            case R.id.llProfile:
                ivBooking.setColorFilter(R.color.dark_gray);
                ivProfile.setColorFilter(R.color.blue);
                replaceFragment(fragmentContainer, new UserProfileFragment(),"");
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            addFragment(fragmentContainer,   DoctorsListFragment.newInstance("Pune"),"");
        } else {
            city = parent.getItemAtPosition(position).toString();
            addFragment(fragmentContainer,   DoctorsListFragment.newInstance(city),"");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}