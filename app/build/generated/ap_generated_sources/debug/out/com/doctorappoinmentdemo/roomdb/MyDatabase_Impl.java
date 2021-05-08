package com.doctorappoinmentdemo.roomdb;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MyDatabase_Impl extends MyDatabase {
  private volatile DaoAccess _daoAccess;

  private volatile TimeSlotDaoAccess _timeSlotDaoAccess;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `todo` (`todo_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `description` TEXT, `category` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `slot_todo` (`todo_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `slot_time` TEXT, `slotStatus` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"cdfef36efd43978998fdfc09f3353a52\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `todo`");
        _db.execSQL("DROP TABLE IF EXISTS `slot_todo`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTodo = new HashMap<String, TableInfo.Column>(4);
        _columnsTodo.put("todo_id", new TableInfo.Column("todo_id", "INTEGER", true, 1));
        _columnsTodo.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsTodo.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsTodo.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTodo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTodo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTodo = new TableInfo("todo", _columnsTodo, _foreignKeysTodo, _indicesTodo);
        final TableInfo _existingTodo = TableInfo.read(_db, "todo");
        if (! _infoTodo.equals(_existingTodo)) {
          throw new IllegalStateException("Migration didn't properly handle todo(com.doctorappoinmentdemo.roomdb.Todo).\n"
                  + " Expected:\n" + _infoTodo + "\n"
                  + " Found:\n" + _existingTodo);
        }
        final HashMap<String, TableInfo.Column> _columnsSlotTodo = new HashMap<String, TableInfo.Column>(3);
        _columnsSlotTodo.put("todo_id", new TableInfo.Column("todo_id", "INTEGER", true, 1));
        _columnsSlotTodo.put("slot_time", new TableInfo.Column("slot_time", "TEXT", false, 0));
        _columnsSlotTodo.put("slotStatus", new TableInfo.Column("slotStatus", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSlotTodo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSlotTodo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSlotTodo = new TableInfo("slot_todo", _columnsSlotTodo, _foreignKeysSlotTodo, _indicesSlotTodo);
        final TableInfo _existingSlotTodo = TableInfo.read(_db, "slot_todo");
        if (! _infoSlotTodo.equals(_existingSlotTodo)) {
          throw new IllegalStateException("Migration didn't properly handle slot_todo(com.doctorappoinmentdemo.roomdb.TimeSlotTodo).\n"
                  + " Expected:\n" + _infoSlotTodo + "\n"
                  + " Found:\n" + _existingSlotTodo);
        }
      }
    }, "cdfef36efd43978998fdfc09f3353a52", "1825357359ecdba20fdb0e78a4324985");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "todo","slot_todo");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `todo`");
      _db.execSQL("DELETE FROM `slot_todo`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DaoAccess daoAccess() {
    if (_daoAccess != null) {
      return _daoAccess;
    } else {
      synchronized(this) {
        if(_daoAccess == null) {
          _daoAccess = new DaoAccess_Impl(this);
        }
        return _daoAccess;
      }
    }
  }

  @Override
  public TimeSlotDaoAccess timeSlotDaoAccess() {
    if (_timeSlotDaoAccess != null) {
      return _timeSlotDaoAccess;
    } else {
      synchronized(this) {
        if(_timeSlotDaoAccess == null) {
          _timeSlotDaoAccess = new TimeSlotDaoAccess_Impl(this);
        }
        return _timeSlotDaoAccess;
      }
    }
  }
}
