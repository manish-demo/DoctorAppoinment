package com.doctorappoinmentdemo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.activity.TimeSlotActivity;
import com.doctorappoinmentdemo.adapter.RvDoctorsListAdaptor;
import com.doctorappoinmentdemo.roomdb.MyDatabase;
import com.doctorappoinmentdemo.roomdb.Todo;

import java.util.ArrayList;
import java.util.List;


public class DoctorsListFragment extends Fragment implements View.OnClickListener  {

    RecyclerView rvDoctorsList;
    RvDoctorsListAdaptor rvDoctorsListAdaptor;
    MyDatabase myDatabase;
    TextView textView;
    ArrayList<Todo> doctorArrayList = new ArrayList<>();

    public static DoctorsListFragment newInstance(String city) {
        DoctorsListFragment myFragment = new DoctorsListFragment();
        Bundle args = new Bundle();
        args.putString("city", city);
        myFragment.setArguments(args);
        return myFragment;
    }

    String city = "";
    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_doctor_list, viewGroup, false);

        rvDoctorsList = rootview.findViewById(R.id.rvDoctorList);
        textView = rootview.findViewById(R.id.tvMsg);
        init();

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     city = getArguments().getString("city");
    }

    private void init(){
        myDatabase = Room.databaseBuilder(getActivity(), MyDatabase.class, MyDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        buildDummyTodos();
    }

    private void setRvDoctorList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDoctorsListAdaptor = new RvDoctorsListAdaptor(getActivity(),this, doctorArrayList);
        rvDoctorsList.setLayoutManager(layoutManager);
        rvDoctorsList.setAdapter(rvDoctorsListAdaptor);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllTodos() {
        new AsyncTask<String, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(String... params) {
                return myDatabase.daoAccess().fetchAllTodos();
            }

            @Override
            protected void onPostExecute(List<Todo> todoList) {
                if (city.equalsIgnoreCase("Pune")){
                    setRvDoctorList();
                }else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }

    private void buildDummyTodos() {
        Todo todo = new Todo();
        todo.name = "Dr. Aditya";
        todo.description = "Heart Specialist";
        todo.category = "Banner";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Megha verma";
        todo.description = "Child Doctor Specialist";
        todo.category = "Kothurd";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Rajesh ";
        todo.description = "Skin care Specialist";
        todo.category = "Banner";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Vaibhav";
        todo.description = "Mind Specialist doctor";
        todo.category = "Banner";

        todo.name = "Dr. Varsha jain";
        todo.description = "Heart Specialist";
        todo.category = "Kothurd";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Himanshu sahu";
        todo.description = "Child Doctor Specialist";
        todo.category = "Banner";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Akash chouhan ";
        todo.description = "Skin care Specialist";
        todo.category = "Kothurd";

        doctorArrayList.add(todo);

        todo = new Todo();
        todo.name = "Dr. Shinde jaiswal";
        todo.description = "Mind Specialist doctor";
        todo.category = "Kothurd";


        doctorArrayList.add(todo);
        insertList(doctorArrayList);
    }

    @SuppressLint("StaticFieldLeak")
    private void insertList(List<Todo> todoList) {
        new AsyncTask<List<Todo>, Void, Void>() {
            @Override
            protected Void doInBackground(List<Todo>... params) {
                myDatabase.daoAccess().insertTodoList(params[0]);

                return null;

            }

            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
                loadAllTodos();
            }
        }.execute(todoList);

    }
    @Override
    public void onClick(View v) {
        int selectedPosition = (int) v.getTag();
        switch (v.getId()){
            case  R.id.rvDoctorListRootLayout:
            Intent intent = new Intent(getActivity(), TimeSlotActivity.class);
            startActivity(intent);
            break;
        }
    }
}
