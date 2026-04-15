package com.budget.manager.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.WorkManager
import com.budget.manager.data.local.BudgetDatabase
import com.budget.manager.data.local.dao.ExpenseDao
import com.budget.manager.data.local.dao.ExpenseCategoryDao
import com.budget.manager.data.local.dao.GrantDao
import com.budget.manager.data.local.dao.WorkspaceDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ─── Room ─────────────────────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideBudgetDatabase(@ApplicationContext context: Context): BudgetDatabase {
        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `custom_categories` (`name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`name`))")
            }
        }

        return Room.databaseBuilder(
            context,
            BudgetDatabase::class.java,
            BudgetDatabase.DATABASE_NAME
        )
            .addMigrations(MIGRATION_5_6)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkspaceDao(database: BudgetDatabase): WorkspaceDao = database.workspaceDao()

    @Provides
    @Singleton
    fun provideExpenseDao(database: BudgetDatabase): ExpenseDao = database.expenseDao()

    @Provides
    @Singleton
    fun provideGrantDao(database: BudgetDatabase): GrantDao = database.grantDao()

    @Provides
    @Singleton
    fun provideExpenseCategoryDao(database: BudgetDatabase): ExpenseCategoryDao = database.expenseCategoryDao()

    // ─── Firebase Firestore ───────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        val firestore = FirebaseFirestore.getInstance()

        // Enable persistent disk cache (Firestore offline support)
        // This is a secondary offline layer — our Room DB is the primary.
        val cacheSettings = PersistentCacheSettings.newBuilder()
            .setSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()

        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(cacheSettings)
            .build()

        firestore.firestoreSettings = settings
        return firestore
    }

    // ─── WorkManager ──────────────────────────────────────────────────────────

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}
