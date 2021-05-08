package com.doctorappoinmentdemo.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.doctorappoinmentdemo.MainActivity;
import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.adapter.RvTimeSlotListAdaptor;
import com.doctorappoinmentdemo.roomdb.MyDatabase;
import com.doctorappoinmentdemo.roomdb.TimeSlotTodo;
import com.doctorappoinmentdemo.utils.AppProgressDialog;
import com.doctorappoinmentdemo.utils.UserSession;
import com.github.tntkhang.gmailsenderlibrary.GMailSender;
import com.github.tntkhang.gmailsenderlibrary.GmailListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.doctorappoinmentdemo.utils.UserSession.PREFER_NAME;


public class TimeSlotTodayFragment extends Fragment implements View.OnClickListener {


    RecyclerView  recyclerView;
    RvTimeSlotListAdaptor rvTimeSlotListAdaptor;
    MyDatabase myDatabase;
    ArrayList<TimeSlotTodo> timeSlotArrayList = new ArrayList<>();
    UserSession session;
    SharedPreferences sharedPreferences;
    AppProgressDialog appProgressDialog;

    @Override
    public View onCreateView
            (LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_choose_time_slot, viewGroup, false);
        recyclerView = rootview.findViewById(R.id.rvTimeSlot);
        init();
        return rootview;
    }

    private void init(){
        appProgressDialog = new AppProgressDialog(getActivity());
        myDatabase = Room.databaseBuilder(getActivity(), MyDatabase.class, MyDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        buildDummyTodos();
        session = new UserSession(getActivity());
        sharedPreferences = getActivity().getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
    }

    private void setRvTimeSlot() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTimeSlotListAdaptor = new RvTimeSlotListAdaptor(getActivity(),this, timeSlotArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvTimeSlotListAdaptor);
    }
    private void buildDummyTodos() {
        timeSlotArrayList.clear();
        TimeSlotTodo todo = new TimeSlotTodo();
        todo.slot_time = "7:45 AM - 8:00 AM";
        todo.slotStatus = 30;

        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "8:00 AM - 8:45 AM";
        todo.slotStatus = 70;

        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "8:45 AM - 9:30 AM";
        todo.slotStatus = 30;

        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "9:30 AM - 10:15 AM";
        todo.slotStatus = 30;

        todo = new TimeSlotTodo();
        todo.slot_time = "10:15 - 11:00 AM";
        todo.slotStatus = 50;
        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "11:00 - 12 PM";
        todo.slotStatus = 50;

        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "12:00 AM - 01:45 AM";
        todo.slotStatus = 70;
        timeSlotArrayList.add(todo);

        todo = new TimeSlotTodo();
        todo.slot_time = "11:00 - 12 PM";
        todo.slotStatus = 50;
        timeSlotArrayList.add(todo);


        insertList(timeSlotArrayList);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllTodos() {
        new AsyncTask<String, Void, List<TimeSlotTodo>>() {
            @Override
            protected List<TimeSlotTodo> doInBackground(String... params) {
                return myDatabase.timeSlotDaoAccess().fetchAllTimeSlot();
            }

            @Override
            protected void onPostExecute(List<TimeSlotTodo> todoList) {
                setRvTimeSlot();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void insertList(List<TimeSlotTodo> todoList) {
        new AsyncTask<List<TimeSlotTodo>, Void, Void>() {
            @Override
            protected Void doInBackground(List<TimeSlotTodo>... params) {
                myDatabase.timeSlotDaoAccess().insertTodoList(params[0]);

                return null;

            }

            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
                loadAllTodos();
            }
        }.execute(todoList);

    }
    private void sendEmail(String timeSlot,String toEmailId){
        appProgressDialog.show();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String userName = "";
        if (sharedPreferences.contains("Name"))
        {
            userName = sharedPreferences.getString("Name", "");
        }

        GMailSender.withAccount("appoinmentdoctor@gmail.com", "manish021@")
                .withTitle("Appointment Confirmation !")
                .withBody("Date : "+formattedDate+"\n\nSubject : Confirmation mail for appointment booking\n\nDear,"+userName+"\n\n" +
                        "This mail is regrading with your appointment confirmation with us we are happy to " +
                        "say that your appointment is successfully booked with us and with your confirmation your time slot will be "+timeSlot+" " +
                        " which is selected by you If your have quarry please free to contact with us on given mail id\n\nThank you" )
                .withSender(getString(R.string.app_name))
                .toEmailAddress(toEmailId) // one or multiple addresses separated by a comma
                .withListenner(new GmailListener() {
                    @Override
                    public void sendSuccess() {
                        appProgressDialog.hide();
                        showMessageOKCancel("Your Booking is Confirmed with us\nPlease check your mail for further detail.",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void sendFail(String err) {
                        appProgressDialog.hide();
                        sendMailIfAddressNotValid(timeSlot);
                    }
                })
                .send();
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void sendMailIfAddressNotValid(String timeSlot){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Appointment Confirmation");
        intent.putExtra(Intent.EXTRA_TEXT, "Appointment Confirmation of your booking  "+timeSlot);

        startActivity(Intent.createChooser(intent, "Send Email.."));
    }

    @Override
    public void onClick(View v) {
        int selectedPosition = (int) v.getTag();
        String email = "";
        switch (v.getId()){
            case R.id.btnJoin:

                if (sharedPreferences.contains("Email"))
                {
                    email = sharedPreferences.getString("Email", "");
                }
                String finalEmail = email;
                showMessageOKCancel("Confirmation !\nPlease make sure the the given mail id " + email + " is correct otherwise you will not received confirmation of your booking on mail.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendEmail(rvTimeSlotListAdaptor.timeSlotTodos.get(selectedPosition).slot_time, finalEmail);
                            }
                        });
                break;
        }
    }
}
