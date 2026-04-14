package com.budget.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.budget.manager.data.local.dao.ExpenseDao
import com.budget.manager.data.local.dao.WorkspaceDao
import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.SyncStatus
import com.budget.manager.data.model.Workspace

class SyncStatusConverter {
    @TypeConverter
    fun fromSyncStatus(status: SyncStatus): String = status.name

    @TypeConverter
    fun toSyncStatus(name: String): SyncStatus = SyncStatus.valueOf(name)
}

@Database(
    entities = [Workspace::class, Expense::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(SyncStatusConverter::class)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "budget_database"
    }
}
