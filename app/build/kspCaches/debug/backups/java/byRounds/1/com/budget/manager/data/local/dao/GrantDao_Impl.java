package com.budget.manager.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.budget.manager.data.local.SyncStatusConverter;
import com.budget.manager.data.model.GrantSettings;
import com.budget.manager.data.model.SyncStatus;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GrantDao_Impl implements GrantDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GrantSettings> __insertionAdapterOfGrantSettings;

  private final SyncStatusConverter __syncStatusConverter = new SyncStatusConverter();

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  public GrantDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGrantSettings = new EntityInsertionAdapter<GrantSettings>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `grant_settings` (`id`,`totalGrant`,`firestoreId`,`syncStatus`,`lastModified`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GrantSettings entity) {
        statement.bindLong(1, entity.getId());
        statement.bindDouble(2, entity.getTotalGrant());
        statement.bindString(3, entity.getFirestoreId());
        final String _tmp = __syncStatusConverter.fromSyncStatus(entity.getSyncStatus());
        statement.bindString(4, _tmp);
        statement.bindLong(5, entity.getLastModified());
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE grant_settings SET syncStatus = 'SYNCED' WHERE id = 1";
        return _query;
      }
    };
  }

  @Override
  public Object upsertGrant(final GrantSettings grantSettings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGrantSettings.insert(grantSettings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markSynced(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<GrantSettings> getGrantSettings() {
    final String _sql = "SELECT * FROM grant_settings WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"grant_settings"}, new Callable<GrantSettings>() {
      @Override
      @Nullable
      public GrantSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotalGrant = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGrant");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final GrantSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpTotalGrant;
            _tmpTotalGrant = _cursor.getDouble(_cursorIndexOfTotalGrant);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new GrantSettings(_tmpId,_tmpTotalGrant,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
          } else {
            _result = null;
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

  @Override
  public Object getGrantSettingsOnce(final Continuation<? super GrantSettings> $completion) {
    final String _sql = "SELECT * FROM grant_settings WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GrantSettings>() {
      @Override
      @Nullable
      public GrantSettings call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotalGrant = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGrant");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final GrantSettings _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpTotalGrant;
            _tmpTotalGrant = _cursor.getDouble(_cursorIndexOfTotalGrant);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new GrantSettings(_tmpId,_tmpTotalGrant,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
