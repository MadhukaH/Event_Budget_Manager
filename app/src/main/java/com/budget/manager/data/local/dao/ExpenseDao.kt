package com.budget.manager.data.local.dao

import androidx.room.*
import com.budget.manager.data.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE workspaceId = :workspaceId AND syncStatus != 'PENDING_DELETE' ORDER BY createdAt DESC")
    fun getExpensesByWorkspace(workspaceId: Long): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Long): Expense?

    @Query("SELECT * FROM expenses WHERE firestoreId = :firestoreId LIMIT 1")
    suspend fun getExpenseByFirestoreId(firestoreId: String): Expense?

    @Query("SELECT SUM(amount) FROM expenses WHERE workspaceId = :workspaceId AND syncStatus != 'PENDING_DELETE'")
    fun getTotalSpendingByWorkspace(workspaceId: Long): Flow<Double?>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE workspaceId = :workspaceId AND syncStatus != 'PENDING_DELETE' GROUP BY category ORDER BY total DESC")
    fun getCategoryTotals(workspaceId: Long): Flow<List<CategoryTotal>>

    /** All expenses not yet in sync with Firestore */
    @Query("SELECT * FROM expenses WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncExpenses(): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("DELETE FROM expenses WHERE workspaceId = :workspaceId")
    suspend fun deleteAllExpensesByWorkspace(workspaceId: Long)

    /**
     * Mark expense as SYNCED — firestoreId was already set at creation.
     * Simply clears the dirty flag.
     */
    @Query("UPDATE expenses SET syncStatus = 'SYNCED' WHERE id = :localId")
    suspend fun markSynced(localId: Long)

    @Query("UPDATE expenses SET syncStatus = 'PENDING_DELETE', lastModified = :timestamp WHERE id = :id")
    suspend fun markPendingDelete(id: Long, timestamp: Long = System.currentTimeMillis())

    /** Remove hard-deleted records from Room after they're removed from Firestore */
    @Query("DELETE FROM expenses WHERE syncStatus = 'PENDING_DELETE'")
    suspend fun purgeDeletedExpenses()
}

data class CategoryTotal(
    val category: String,
    val total: Double
)
