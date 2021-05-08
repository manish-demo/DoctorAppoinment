package com.doctorappoinmentdemo.roomdb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class,TimeSlotTodo.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";
    public static final String TABLE_NAME_TODO = "todo";
    public static final String TIME_SLOT_TABLE = "slot_todo";
    public abstract DaoAccess daoAccess();
    public abstract TimeSlotDaoAccess timeSlotDaoAccess();

}
