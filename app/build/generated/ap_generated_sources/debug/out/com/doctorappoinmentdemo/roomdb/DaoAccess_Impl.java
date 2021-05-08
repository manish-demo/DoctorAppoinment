package com.doctorappoinmentdemo.roomdb;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
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
public final class DaoAccess_Impl implements DaoAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTodo;

  public DaoAccess_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodo = new EntityInsertionAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `todo`(`todo_id`,`name`,`description`,`category`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.todo_id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        if (value.category == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.category);
        }
      }
    };
    this.__deletionAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `todo` WHERE `todo_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.todo_id);
      }
    };
    this.__updateAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `todo` SET `todo_id` = ?,`name` = ?,`description` = ?,`category` = ? WHERE `todo_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.todo_id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.description == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.description);
        }
        if (value.category == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.category);
        }
        stmt.bindLong(5, value.todo_id);
      }
    };
  }

  @Override
  public long insertTodo(Todo todo) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTodo.insertAndReturnId(todo);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertTodoList(List<Todo> todoList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTodo.insert(todoList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteTodo(Todo todo) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfTodo.handle(todo);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateTodo(Todo todo) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfTodo.handle(todo);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Todo> fetchAllTodos() {
    final String _sql = "SELECT * FROM todo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTodoId = _cursor.getColumnIndexOrThrow("todo_id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final List<Todo> _result = new ArrayList<Todo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Todo _item;
        _item = new Todo();
        _item.todo_id = _cursor.getInt(_cursorIndexOfTodoId);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.description = _cursor.getString(_cursorIndexOfDescription);
        _item.category = _cursor.getString(_cursorIndexOfCategory);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Todo> fetchTodoListByCategory(String category) {
    final String _sql = "SELECT * FROM todo WHERE category = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTodoId = _cursor.getColumnIndexOrThrow("todo_id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final List<Todo> _result = new ArrayList<Todo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Todo _item;
        _item = new Todo();
        _item.todo_id = _cursor.getInt(_cursorIndexOfTodoId);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.description = _cursor.getString(_cursorIndexOfDescription);
        _item.category = _cursor.getString(_cursorIndexOfCategory);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Todo fetchTodoListById(int todoId) {
    final String _sql = "SELECT * FROM todo WHERE todo_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, todoId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTodoId = _cursor.getColumnIndexOrThrow("todo_id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final Todo _result;
      if(_cursor.moveToFirst()) {
        _result = new Todo();
        _result.todo_id = _cursor.getInt(_cursorIndexOfTodoId);
        _result.name = _cursor.getString(_cursorIndexOfName);
        _result.description = _cursor.getString(_cursorIndexOfDescription);
        _result.category = _cursor.getString(_cursorIndexOfCategory);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
