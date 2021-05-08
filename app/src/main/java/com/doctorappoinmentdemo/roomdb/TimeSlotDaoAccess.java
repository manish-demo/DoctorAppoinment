package com.doctorappoinmentdemo.roomdb;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeSlotDaoAccess {

    @Insert
    long insertTodo(TimeSlotTodo todo);

    @Insert
    void insertTodoList(List<TimeSlotTodo> todoList);

    @Query("SELECT * FROM " + MyDatabase.TIME_SLOT_TABLE)
    List<TimeSlotTodo> fetchAllTimeSlot();
}
