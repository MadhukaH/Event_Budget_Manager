package com.budget.manager.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.budget.manager.data.local.dao.ExpenseCategoryDao;
import com.budget.manager.data.local.dao.ExpenseCategoryDao_Impl;
import com.budget.manager.data.local.dao.ExpenseDao;
import com.budget.manager.data.local.dao.ExpenseDao_Impl;
import com.budget.manager.data.local.dao.GrantDao;
import com.budget.manager.data.local.dao.GrantDao_Impl;
import com.budget.manager.data.local.dao.WorkspaceDao;
import com.budget.manager.data.local.dao.WorkspaceDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDatabase_Impl extends BudgetDatabase {
  private volatile WorkspaceDao _workspaceDao;

  private volatile ExpenseDao _expenseDao;

  private volatile GrantDao _grantDao;

  private volatile ExpenseCategoryDao _expenseCategoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `workspaces` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `totalBudget` REAL NOT NULL, `createdAt` INTEGER NOT NULL, `colorIndex` INTEGER NOT NULL, `firestoreId` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, `lastModified` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workspaceId` INTEGER NOT NULL, `category` TEXT NOT NULL, `amount` REAL NOT NULL, `note` TEXT NOT NULL, `receiptBase64` TEXT, `createdAt` INTEGER NOT NULL, `firestoreId` TEXT NOT NULL, `workspaceFirestoreId` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, `lastModified` INTEGER NOT NULL, FOREIGN KEY(`workspaceId`) REFERENCES `workspaces`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_workspaceId` ON `expenses` (`workspaceId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_syncStatus` ON `expenses` (`syncStatus`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_firestoreId` ON `expenses` (`firestoreId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `grant_settings` (`id` INTEGER NOT NULL, `totalGrant` REAL NOT NULL, `firestoreId` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, `lastModified` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `custom_categories` (`name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`name`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '053c16d3549a9541ff9107f2354a7d45')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `workspaces`");
        db.execSQL("DROP TABLE IF EXISTS `expenses`");
        db.execSQL("DROP TABLE IF EXISTS `grant_settings`");
        db.execSQL("DROP TABLE IF EXISTS `custom_categories`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWorkspaces = new HashMap<String, TableInfo.Column>(9);
        _columnsWorkspaces.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("totalBudget", new TableInfo.Column("totalBudget", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("colorIndex", new TableInfo.Column("colorIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("firestoreId", new TableInfo.Column("firestoreId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkspaces.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWorkspaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWorkspaces = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkspaces = new TableInfo("workspaces", _columnsWorkspaces, _foreignKeysWorkspaces, _indicesWorkspaces);
        final TableInfo _existingWorkspaces = TableInfo.read(db, "workspaces");
        if (!_infoWorkspaces.equals(_existingWorkspaces)) {
          return new RoomOpenHelper.ValidationResult(false, "workspaces(com.budget.manager.data.model.Workspace).\n"
                  + " Expected:\n" + _infoWorkspaces + "\n"
                  + " Found:\n" + _existingWorkspaces);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(11);
        _columnsExpenses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("workspaceId", new TableInfo.Column("workspaceId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("receiptBase64", new TableInfo.Column("receiptBase64", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("firestoreId", new TableInfo.Column("firestoreId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("workspaceFirestoreId", new TableInfo.Column("workspaceFirestoreId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("workspaces", "CASCADE", "NO ACTION", Arrays.asList("workspaceId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(3);
        _indicesExpenses.add(new TableInfo.Index("index_expenses_workspaceId", false, Arrays.asList("workspaceId"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_syncStatus", false, Arrays.asList("syncStatus"), Arrays.asList("ASC")));
        _indicesExpenses.add(new TableInfo.Index("index_expenses_firestoreId", false, Arrays.asList("firestoreId"), Arrays.asList("ASC")));
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(db, "expenses");
        if (!_infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.budget.manager.data.model.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsGrantSettings = new HashMap<String, TableInfo.Column>(5);
        _columnsGrantSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrantSettings.put("totalGrant", new TableInfo.Column("totalGrant", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrantSettings.put("firestoreId", new TableInfo.Column("firestoreId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrantSettings.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrantSettings.put("lastModified", new TableInfo.Column("lastModified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGrantSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGrantSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGrantSettings = new TableInfo("grant_settings", _columnsGrantSettings, _foreignKeysGrantSettings, _indicesGrantSettings);
        final TableInfo _existingGrantSettings = TableInfo.read(db, "grant_settings");
        if (!_infoGrantSettings.equals(_existingGrantSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "grant_settings(com.budget.manager.data.model.GrantSettings).\n"
                  + " Expected:\n" + _infoGrantSettings + "\n"
                  + " Found:\n" + _existingGrantSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsCustomCategories = new HashMap<String, TableInfo.Column>(2);
        _columnsCustomCategories.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCategories.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomCategories = new TableInfo("custom_categories", _columnsCustomCategories, _foreignKeysCustomCategories, _indicesCustomCategories);
        final TableInfo _existingCustomCategories = TableInfo.read(db, "custom_categories");
        if (!_infoCustomCategories.equals(_existingCustomCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "custom_categories(com.budget.manager.data.model.ExpenseCategory).\n"
                  + " Expected:\n" + _infoCustomCategories + "\n"
                  + " Found:\n" + _existingCustomCategories);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "053c16d3549a9541ff9107f2354a7d45", "d24e1a381abfadb25e6aae351966b1cd");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "workspaces","expenses","grant_settings","custom_categories");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `workspaces`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `grant_settings`");
      _db.execSQL("DELETE FROM `custom_categories`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WorkspaceDao.class, WorkspaceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GrantDao.class, GrantDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseCategoryDao.class, ExpenseCategoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WorkspaceDao workspaceDao() {
    if (_workspaceDao != null) {
      return _workspaceDao;
    } else {
      synchronized(this) {
        if(_workspaceDao == null) {
          _workspaceDao = new WorkspaceDao_Impl(this);
        }
        return _workspaceDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public GrantDao grantDao() {
    if (_grantDao != null) {
      return _grantDao;
    } else {
      synchronized(this) {
        if(_grantDao == null) {
          _grantDao = new GrantDao_Impl(this);
        }
        return _grantDao;
      }
    }
  }

  @Override
  public ExpenseCategoryDao expenseCategoryDao() {
    if (_expenseCategoryDao != null) {
      return _expenseCategoryDao;
    } else {
      synchronized(this) {
        if(_expenseCategoryDao == null) {
          _expenseCategoryDao = new ExpenseCategoryDao_Impl(this);
        }
        return _expenseCategoryDao;
      }
    }
  }
}
