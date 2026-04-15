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
import com.budget.manager.data.model.Expense;
import com.budget.manager.data.model.SyncStatus;
import java.lang.Class;
import java.lang.Double;
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
public final class ExpenseDao_Impl implements ExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Expense> __insertionAdapterOfExpense;

  private final SyncStatusConverter __syncStatusConverter = new SyncStatusConverter();

  private final EntityDeletionOrUpdateAdapter<Expense> __deletionAdapterOfExpense;

  private final EntityDeletionOrUpdateAdapter<Expense> __updateAdapterOfExpense;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllExpensesByWorkspace;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkPendingDelete;

  private final SharedSQLiteStatement __preparedStmtOfPurgeDeletedExpenses;

  public ExpenseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpense = new EntityInsertionAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `expenses` (`id`,`workspaceId`,`category`,`amount`,`note`,`receiptBase64`,`createdAt`,`firestoreId`,`workspaceFirestoreId`,`syncStatus`,`lastModified`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Expense entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkspaceId());
        statement.bindString(3, entity.getCategory());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getNote());
        if (entity.getReceiptBase64() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReceiptBase64());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindString(8, entity.getFirestoreId());
        statement.bindString(9, entity.getWorkspaceFirestoreId());
        final String _tmp = __syncStatusConverter.fromSyncStatus(entity.getSyncStatus());
        statement.bindString(10, _tmp);
        statement.bindLong(11, entity.getLastModified());
      }
    };
    this.__deletionAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `expenses` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Expense entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `expenses` SET `id` = ?,`workspaceId` = ?,`category` = ?,`amount` = ?,`note` = ?,`receiptBase64` = ?,`createdAt` = ?,`firestoreId` = ?,`workspaceFirestoreId` = ?,`syncStatus` = ?,`lastModified` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Expense entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getWorkspaceId());
        statement.bindString(3, entity.getCategory());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getNote());
        if (entity.getReceiptBase64() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReceiptBase64());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindString(8, entity.getFirestoreId());
        statement.bindString(9, entity.getWorkspaceFirestoreId());
        final String _tmp = __syncStatusConverter.fromSyncStatus(entity.getSyncStatus());
        statement.bindString(10, _tmp);
        statement.bindLong(11, entity.getLastModified());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllExpensesByWorkspace = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM expenses WHERE workspaceId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE expenses SET syncStatus = 'SYNCED' WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPendingDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE expenses SET syncStatus = 'PENDING_DELETE', lastModified = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeDeletedExpenses = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM expenses WHERE syncStatus = 'PENDING_DELETE'";
        return _query;
      }
    };
  }

  @Override
  public Object insertExpense(final Expense expense, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfExpense.insertAndReturnId(expense);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteExpense(final Expense expense, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfExpense.handle(expense);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateExpense(final Expense expense, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfExpense.handle(expense);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllExpensesByWorkspace(final long workspaceId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllExpensesByWorkspace.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, workspaceId);
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
          __preparedStmtOfDeleteAllExpensesByWorkspace.release(_stmt);
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
  public Object purgeDeletedExpenses(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeDeletedExpenses.acquire();
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
          __preparedStmtOfPurgeDeletedExpenses.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Expense>> getExpensesByWorkspace(final long workspaceId) {
    final String _sql = "SELECT * FROM expenses WHERE workspaceId = ? AND syncStatus != 'PENDING_DELETE' ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workspaceId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"expenses"}, new Callable<List<Expense>>() {
      @Override
      @NonNull
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkspaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfReceiptBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptBase64");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfWorkspaceFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceFirestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkspaceId;
            _tmpWorkspaceId = _cursor.getLong(_cursorIndexOfWorkspaceId);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpReceiptBase64;
            if (_cursor.isNull(_cursorIndexOfReceiptBase64)) {
              _tmpReceiptBase64 = null;
            } else {
              _tmpReceiptBase64 = _cursor.getString(_cursorIndexOfReceiptBase64);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final String _tmpWorkspaceFirestoreId;
            _tmpWorkspaceFirestoreId = _cursor.getString(_cursorIndexOfWorkspaceFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Expense(_tmpId,_tmpWorkspaceId,_tmpCategory,_tmpAmount,_tmpNote,_tmpReceiptBase64,_tmpCreatedAt,_tmpFirestoreId,_tmpWorkspaceFirestoreId,_tmpSyncStatus,_tmpLastModified);
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
  public Object getAllExpensesOnce(final Continuation<? super List<Expense>> $completion) {
    final String _sql = "SELECT * FROM expenses WHERE syncStatus != 'PENDING_DELETE' ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Expense>>() {
      @Override
      @NonNull
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkspaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfReceiptBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptBase64");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfWorkspaceFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceFirestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkspaceId;
            _tmpWorkspaceId = _cursor.getLong(_cursorIndexOfWorkspaceId);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpReceiptBase64;
            if (_cursor.isNull(_cursorIndexOfReceiptBase64)) {
              _tmpReceiptBase64 = null;
            } else {
              _tmpReceiptBase64 = _cursor.getString(_cursorIndexOfReceiptBase64);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final String _tmpWorkspaceFirestoreId;
            _tmpWorkspaceFirestoreId = _cursor.getString(_cursorIndexOfWorkspaceFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Expense(_tmpId,_tmpWorkspaceId,_tmpCategory,_tmpAmount,_tmpNote,_tmpReceiptBase64,_tmpCreatedAt,_tmpFirestoreId,_tmpWorkspaceFirestoreId,_tmpSyncStatus,_tmpLastModified);
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

  @Override
  public Object getExpenseById(final long id, final Continuation<? super Expense> $completion) {
    final String _sql = "SELECT * FROM expenses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Expense>() {
      @Override
      @Nullable
      public Expense call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkspaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfReceiptBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptBase64");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfWorkspaceFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceFirestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Expense _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkspaceId;
            _tmpWorkspaceId = _cursor.getLong(_cursorIndexOfWorkspaceId);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpReceiptBase64;
            if (_cursor.isNull(_cursorIndexOfReceiptBase64)) {
              _tmpReceiptBase64 = null;
            } else {
              _tmpReceiptBase64 = _cursor.getString(_cursorIndexOfReceiptBase64);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final String _tmpWorkspaceFirestoreId;
            _tmpWorkspaceFirestoreId = _cursor.getString(_cursorIndexOfWorkspaceFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Expense(_tmpId,_tmpWorkspaceId,_tmpCategory,_tmpAmount,_tmpNote,_tmpReceiptBase64,_tmpCreatedAt,_tmpFirestoreId,_tmpWorkspaceFirestoreId,_tmpSyncStatus,_tmpLastModified);
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
  public Object getExpenseByFirestoreId(final String firestoreId,
      final Continuation<? super Expense> $completion) {
    final String _sql = "SELECT * FROM expenses WHERE firestoreId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, firestoreId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Expense>() {
      @Override
      @Nullable
      public Expense call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkspaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfReceiptBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptBase64");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfWorkspaceFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceFirestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final Expense _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkspaceId;
            _tmpWorkspaceId = _cursor.getLong(_cursorIndexOfWorkspaceId);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpReceiptBase64;
            if (_cursor.isNull(_cursorIndexOfReceiptBase64)) {
              _tmpReceiptBase64 = null;
            } else {
              _tmpReceiptBase64 = _cursor.getString(_cursorIndexOfReceiptBase64);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final String _tmpWorkspaceFirestoreId;
            _tmpWorkspaceFirestoreId = _cursor.getString(_cursorIndexOfWorkspaceFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _result = new Expense(_tmpId,_tmpWorkspaceId,_tmpCategory,_tmpAmount,_tmpNote,_tmpReceiptBase64,_tmpCreatedAt,_tmpFirestoreId,_tmpWorkspaceFirestoreId,_tmpSyncStatus,_tmpLastModified);
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
  public Flow<Double> getTotalSpendingByWorkspace(final long workspaceId) {
    final String _sql = "SELECT SUM(amount) FROM expenses WHERE workspaceId = ? AND syncStatus != 'PENDING_DELETE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workspaceId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"expenses"}, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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
  public Flow<List<CategoryTotal>> getCategoryTotals(final long workspaceId) {
    final String _sql = "SELECT category, SUM(amount) as total FROM expenses WHERE workspaceId = ? AND syncStatus != 'PENDING_DELETE' GROUP BY category ORDER BY total DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, workspaceId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"expenses"}, new Callable<List<CategoryTotal>>() {
      @Override
      @NonNull
      public List<CategoryTotal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfTotal = 1;
          final List<CategoryTotal> _result = new ArrayList<CategoryTotal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryTotal _item;
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            _item = new CategoryTotal(_tmpCategory,_tmpTotal);
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
  public Object getPendingSyncExpenses(final Continuation<? super List<Expense>> $completion) {
    final String _sql = "SELECT * FROM expenses WHERE syncStatus != 'SYNCED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Expense>>() {
      @Override
      @NonNull
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWorkspaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfReceiptBase64 = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptBase64");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfWorkspaceFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "workspaceFirestoreId");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfLastModified = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModified");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWorkspaceId;
            _tmpWorkspaceId = _cursor.getLong(_cursorIndexOfWorkspaceId);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final String _tmpReceiptBase64;
            if (_cursor.isNull(_cursorIndexOfReceiptBase64)) {
              _tmpReceiptBase64 = null;
            } else {
              _tmpReceiptBase64 = _cursor.getString(_cursorIndexOfReceiptBase64);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final String _tmpFirestoreId;
            _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            final String _tmpWorkspaceFirestoreId;
            _tmpWorkspaceFirestoreId = _cursor.getString(_cursorIndexOfWorkspaceFirestoreId);
            final SyncStatus _tmpSyncStatus;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfSyncStatus);
            _tmpSyncStatus = __syncStatusConverter.toSyncStatus(_tmp);
            final long _tmpLastModified;
            _tmpLastModified = _cursor.getLong(_cursorIndexOfLastModified);
            _item = new Expense(_tmpId,_tmpWorkspaceId,_tmpCategory,_tmpAmount,_tmpNote,_tmpReceiptBase64,_tmpCreatedAt,_tmpFirestoreId,_tmpWorkspaceFirestoreId,_tmpSyncStatus,_tmpLastModified);
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
