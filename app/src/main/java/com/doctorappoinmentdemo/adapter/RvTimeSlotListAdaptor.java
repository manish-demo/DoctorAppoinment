package com.doctorappoinmentdemo.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorappoinmentdemo.R;
import com.doctorappoinmentdemo.roomdb.TimeSlotTodo;

import java.util.ArrayList;

public class RvTimeSlotListAdaptor extends RecyclerView.Adapter<RvTimeSlotListAdaptor.MyHolder> {

    private MyHolder holder;
    private Context mContext;
    private View.OnClickListener mClickLisner;
    public ArrayList<TimeSlotTodo> timeSlotTodos;

    public RvTimeSlotListAdaptor(Context mContext, View.OnClickListener mClickLisner,ArrayList<TimeSlotTodo> timeSlotTodos) {
        this.mContext = mContext;
        this.mClickLisner = mClickLisner;
        this.timeSlotTodos = timeSlotTodos;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =
                LayoutInflater.from(mContext);

        View v = inflater.inflate(R.layout.adapter_time_slot, viewGroup,
                false);

        holder = new MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(
            @NonNull final MyHolder myHolder, final int position) {
        TimeSlotTodo timeSlotTodo = timeSlotTodos.get(position);
        holder.tvSlotTime.setText(timeSlotTodo.slot_time);
        holder.seekBar.setProgress(timeSlotTodo.slotStatus);
        holder.btnJoin.setTag(position);
        holder.btnJoin.setOnClickListener(mClickLisner);
        if (timeSlotTodo.slotStatus==30){
            holder.seekBar.getProgressDrawable().setColorFilter(mContext.getResources().getColor(R.color.green), PorterDuff.Mode.MULTIPLY);
            holder.tvSlotStatus.setText("Available");
        }else if (timeSlotTodo.slotStatus==50){
            holder.seekBar.getProgressDrawable().setColorFilter(mContext.getResources().getColor(R.color.yellow), PorterDuff.Mode.MULTIPLY);
            holder.tvSlotStatus.setText("FILING FAST");
        }else {
            holder.seekBar.getProgressDrawable().setColorFilter(mContext.getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
            holder.tvSlotStatus.setText("ALMOST FULL");
        }
    }

    @Override
    public int getItemCount() {
        return timeSlotTodos.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tvSlotTime,tvSlotStatus;
         SeekBar seekBar;
        Button btnJoin;

        public MyHolder(@NonNull View v) {
            super(v);
        tvSlotStatus = v.findViewById(R.id.tvStatus);
        tvSlotTime = v.findViewById(R.id.tvTimeSlot);
        seekBar = v.findViewById(R.id.seekBarStatusPercentage);
        btnJoin = v.findViewById(R.id.btnJoin);
        }
    }
}


