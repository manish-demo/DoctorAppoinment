package com.doctorappoinmentdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.activity.TimeSlotActivity;
import com.doctorappoinmentdemo.roomdb.Todo;

import java.util.ArrayList;

public class RvDoctorsListAdaptor extends RecyclerView.Adapter<RvDoctorsListAdaptor.MyHolder> {

    private MyHolder holder;
    private Context mContext;
    private View.OnClickListener mClickLisner;
    private ArrayList<Todo> doctorArrayList;

    public RvDoctorsListAdaptor(Context mContext,View.OnClickListener mClickLisner,ArrayList<Todo> doctorArrayList) {
        this.mContext = mContext;
        this.mClickLisner = mClickLisner;
        this.doctorArrayList  = doctorArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =
                LayoutInflater.from(mContext);

        View v = inflater.inflate(R.layout.adapter_doctor_list, viewGroup,
                false);

        holder = new MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(
            @NonNull final MyHolder myHolder, final int position) {
        Todo todo = doctorArrayList.get(position);
        holder.tvDocName.setText(todo.name);
        holder.tvDoctorSpeciality.setText(todo.description);
        holder.tvDoctorCategory.setText(todo.category);
        holder.rootLayout.setTag(position);
        holder.rootLayout.setOnClickListener(mClickLisner);
        holder.ivBookMark.setVisibility(View.INVISIBLE);
        if (position==0||position ==2){
            holder.ivBookMark.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvDocName, tvDoctorSpeciality,tvDoctorCategory;
        RelativeLayout rootLayout;
        ImageView ivBookMark;

        public MyHolder(@NonNull View v) {
            super(v);
            ivBookMark = v.findViewById(R.id.ivBookmark);
            rootLayout = v.findViewById(R.id.rvDoctorListRootLayout);
            tvDocName = v.findViewById(R.id.tvDoctorNamr);
            tvDoctorSpeciality = v.findViewById(R.id.tvDoctorSpeciality);
            tvDoctorCategory = v.findViewById(R.id.tvDocCategory);
        }
    }
}


