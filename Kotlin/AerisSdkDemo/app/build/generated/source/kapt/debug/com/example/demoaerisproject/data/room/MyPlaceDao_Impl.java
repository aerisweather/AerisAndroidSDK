package com.example.demoaerisproject.data.room;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyPlaceDao_Impl implements MyPlaceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MyPlace> __insertionAdapterOfMyPlace;

  private final EntityDeletionOrUpdateAdapter<MyPlace> __updateAdapterOfMyPlace;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByName;

  private final SharedSQLiteStatement __preparedStmtOfResetMyLocFalse;

  public MyPlaceDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMyPlace = new EntityInsertionAdapter<MyPlace>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `my_place_table` (`name`,`state`,`country`,`myLoc`,`latitude`,`longitude`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyPlace value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getState() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getState());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCountry());
        }
        final int _tmp = value.getMyLoc() ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindDouble(5, value.getLatitude());
        stmt.bindDouble(6, value.getLongitude());
      }
    };
    this.__updateAdapterOfMyPlace = new EntityDeletionOrUpdateAdapter<MyPlace>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `my_place_table` SET `name` = ?,`state` = ?,`country` = ?,`myLoc` = ?,`latitude` = ?,`longitude` = ? WHERE `name` = ? AND `state` = ? AND `country` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyPlace value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getState() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getState());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCountry());
        }
        final int _tmp = value.getMyLoc() ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindDouble(5, value.getLatitude());
        stmt.bindDouble(6, value.getLongitude());
        if (value.getName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getName());
        }
        if (value.getState() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getState());
        }
        if (value.getCountry() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCountry());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM my_place_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM my_place_table WHERE name = ?";
        return _query;
      }
    };
    this.__preparedStmtOfResetMyLocFalse = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE my_place_table SET myLoc = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MyPlace myPlace, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMyPlace.insert(myPlace);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object update(final MyPlace myPlace, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMyPlace.handle(myPlace);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteByName(final String selected, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByName.acquire();
        int _argIndex = 1;
        if (selected == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, selected);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteByName.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object resetMyLocFalse(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfResetMyLocFalse.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfResetMyLocFalse.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<MyPlace>> getAllMyPlaces() {
    final String _sql = "SELECT * from my_place_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"my_place_table"}, new Callable<List<MyPlace>>() {
      @Override
      public List<MyPlace> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
          final int _cursorIndexOfCountry = CursorUtil.getColumnIndexOrThrow(_cursor, "country");
          final int _cursorIndexOfMyLoc = CursorUtil.getColumnIndexOrThrow(_cursor, "myLoc");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<MyPlace> _result = new ArrayList<MyPlace>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MyPlace _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpState;
            if (_cursor.isNull(_cursorIndexOfState)) {
              _tmpState = null;
            } else {
              _tmpState = _cursor.getString(_cursorIndexOfState);
            }
            final String _tmpCountry;
            if (_cursor.isNull(_cursorIndexOfCountry)) {
              _tmpCountry = null;
            } else {
              _tmpCountry = _cursor.getString(_cursorIndexOfCountry);
            }
            final boolean _tmpMyLoc;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMyLoc);
            _tmpMyLoc = _tmp != 0;
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _item = new MyPlace(_tmpName,_tmpState,_tmpCountry,_tmpMyLoc,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
