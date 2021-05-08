package com.doctorappoinmentdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.adapter.RvDoctorsListAdaptor;


public class DummyFragment extends Fragment  {


    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.dummy_fragment, viewGroup, false);

        return rootview;
    }

}
