package com.budget.manager.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.budget.manager.data.local.SyncStatusConverter;
import com.budget.manager.data.model.SyncStatus;
import com.budget.manager.data.model.Workspace;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WorkspaceDao_Impl implements WorkspaceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Workspace> __insertionAdapterOfWorkspace;

  private final SyncStatusConverter __syncStatusConverter = new SyncStatusConverter();

  private final EntityDeletionOrUpdateAdapter<Workspace> __deletionAdapterOfWorkspace;

  private final EntityDeletionOrUpdateAdapter<Workspace> __updateAdapterOfWorkspace;

  private final SharedSQLiteStatement __preparedStmtOfDeleteWorkspaceById;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkPendingDelete;

  public WorkspaceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkspace = new EntityInsertionAdapter<Workspace>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workspaces` (`id`,`name`,`description`,`totalBudget`,`createdAt`,`colorIndex`,`firestoreId`,`syncStatus`,`lastModified`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workspace entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindDouble(4, entity.getTotalBudget());
        statement.bindLong(5, entity.getCreatedAt());
        statement.bindLong(6, entity.getColorIndex());
        statement.bindString(7, entity.getFirestoreId());
        final String _tmp = __syncStatusConverter.fromSyncStatus(entity.getSyncStatus());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getLastModified());
      }
    };
    this.__deletionAdapterOfWorkspace = new EntityDeletionOrUpdateAdapter<Workspace>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workspaces` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workspace entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWorkspace = new EntityDeletionOrUpdateAdapter<Workspace>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workspaces` SET `id` = ?,`name` = ?,`description` = ?,`totalBudget` = ?,`createdAt` = ?,`colorIndex` = ?,`firestoreId` = ?,`syncStatus` = ?,`lastModified` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Workspace entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDescription());
        statement.bindDouble(4, entity.getTotalBudget());
        statement.bindLong(5, entity.getCreatedAt());
        statement.bindLong(6, entity.getColorIndex());
        statement.bindString(7, entity.getFirestoreId());
        final String _tmp = __syncStatusConverter.fromSyncStatus(entity.getSyncStatus());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getLastModified());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteWorkspaceById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM workspaces WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE workspaces SET syncStatus = 'SYNCED' WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPendingDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE workspaces SET syncStatus = 'PENDING_DELETE', lastModified = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWorkspace(final Workspace workspace,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkspace.insertAndReturnId(workspace);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkspace(final Workspace workspace,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkspace.handle(workspace);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkspace(final Workspace workspace,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkspace.handle(workspace);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkspaceById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteWorkspaceById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteWorkspaceById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSynced(final long localId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, localId);
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
  public Object markPendingDelete(final long id, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPendingDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfMarkPendingDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Workspace>> getAllWorkspaces() {
    final String _sql = "SELECT * FROM workspaces WHERE syncStatus != 'PENDING_DELETE' ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workspaces"}, new Callable<List<Workspace>>() {
      @Override
      @NonNull
      public List<Workspace> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfColorIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorIndex");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Workspace> _result = new ArrayList<Workspace>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workspace _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpColorIndex;
            _tmpColorIndex = _cursor.getInt(_cursorIndexOfColorIndex);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Workspace(_tmpId,_tmpName,_tmpDescription,_tmpTotalBudget,_tmpCreatedAt,_tmpColorIndex,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
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

  @Override
  public Flow<Workspace> getWorkspaceById(final long id) {
    final String _sql = "SELECT * FROM workspaces WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workspaces"}, new Callable<Workspace>() {
      @Override
      @Nullable
      public Workspace call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfColorIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorIndex");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Workspace _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpColorIndex;
            _tmpColorIndex = _cursor.getInt(_cursorIndexOfColorIndex);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Workspace(_tmpId,_tmpName,_tmpDescription,_tmpTotalBudget,_tmpCreatedAt,_tmpColorIndex,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
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
  public Object getWorkspaceByIdAsync(final long id,
      final Continuation<? super Workspace> $completion) {
    final String _sql = "SELECT * FROM workspaces WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Workspace>() {
      @Override
      @Nullable
      public Workspace call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfColorIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorIndex");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Workspace _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpColorIndex;
            _tmpColorIndex = _cursor.getInt(_cursorIndexOfColorIndex);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Workspace(_tmpId,_tmpName,_tmpDescription,_tmpTotalBudget,_tmpCreatedAt,_tmpColorIndex,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
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

  @Override
  public Object getWorkspaceByFirestoreId(final String firestoreId,
      final Continuation<? super Workspace> $completion) {
    final String _sql = "SELECT * FROM workspaces WHERE firestoreId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, firestoreId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Workspace>() {
      @Override
      @Nullable
      public Workspace call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfColorIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorIndex");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Workspace _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpColorIndex;
            _tmpColorIndex = _cursor.getInt(_cursorIndexOfColorIndex);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Workspace(_tmpId,_tmpName,_tmpDescription,_tmpTotalBudget,_tmpCreatedAt,_tmpColorIndex,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
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

  @Override
  public Object getPendingSyncWorkspaces(final Continuation<? super List<Workspace>> $completion) {
    final String _sql = "SELECT * FROM workspaces WHERE syncStatus != 'SYNCED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Workspace>>() {
      @Override
      @NonNull
      public List<Workspace> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfTotalBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "totalBudget");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfColorIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorIndex");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Workspace> _result = new ArrayList<Workspace>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Workspace _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final double _tmpTotalBudget;
            _tmpTotalBudget = _cursor.getDouble(_cursorIndexOfTotalBudget);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final int _tmpColorIndex;
            _tmpColorIndex = _cursor.getInt(_cursorIndexOfColorIndex);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Workspace(_tmpId,_tmpName,_tmpDescription,_tmpTotalBudget,_tmpCreatedAt,_tmpColorIndex,_tmpFirestoreId,_tmpSyncStatus,_tmpLastModified);
            _result.add(_item);
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
