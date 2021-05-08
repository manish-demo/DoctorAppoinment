package com.doctorappoinmentdemo.roomdb;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class TimeSlotDaoAccess_Impl implements TimeSlotDaoAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTimeSlotTodo;

  public TimeSlotDaoAccess_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTimeSlotTodo = new EntityInsertionAdapter<TimeSlotTodo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `slot_todo`(`todo_id`,`slot_time`,`slotStatus`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TimeSlotTodo value) {
        stmt.bindLong(1, value.todo_id);
        if (value.slot_time == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.slot_time);
        }
        if (value.slotStatus == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, value.slotStatus);
        }
      }
    };
  }

  @Override
  public long insertTodo(TimeSlotTodo todo) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTimeSlotTodo.insertAndReturnId(todo);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertTodoList(List<TimeSlotTodo> todoList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTimeSlotTodo.insert(todoList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TimeSlotTodo> fetchAllTimeSlot() {
    final String _sql = "SELECT * FROM slot_todo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTodoId = _cursor.getColumnIndexOrThrow("todo_id");
      final int _cursorIndexOfSlotTime = _cursor.getColumnIndexOrThrow("slot_time");
      final int _cursorIndexOfSlotStatus = _cursor.getColumnIndexOrThrow("slotStatus");
      final List<TimeSlotTodo> _result = new ArrayList<TimeSlotTodo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TimeSlotTodo _item;
        _item = new TimeSlotTodo();
        _item.todo_id = _cursor.getInt(_cursorIndexOfTodoId);
        _item.slot_time = _cursor.getString(_cursorIndexOfSlotTime);
        if (_cursor.isNull(_cursorIndexOfSlotStatus)) {
          _item.slotStatus = null;
        } else {
          _item.slotStatus = _cursor.getInt(_cursorIndexOfSlotStatus);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
