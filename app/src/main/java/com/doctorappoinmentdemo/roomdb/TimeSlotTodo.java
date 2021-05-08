package com.doctorappoinmentdemo.roomdb;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = MyDatabase.TIME_SLOT_TABLE)
public class TimeSlotTodo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int todo_id;

    public String slot_time;

    public Integer slotStatus;

    @Ignore
    public String priority;

}
