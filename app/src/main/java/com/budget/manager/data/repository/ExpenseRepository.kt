package com.budget.manager.data.repository

import com.budget.manager.data.local.dao.CategoryTotal
import com.budget.manager.data.local.dao.ExpenseDao
import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.SyncStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ExpenseRepository — single source of truth for expense data.
 *
 * Same offline-first strategy as WorkspaceRepository:
 * Room is always written first, sync status flags the record,
 * and background workers push changes to Firestore.
 */
@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    // ─── Reads ────────────────────────────────────────────────────────────────

    fun getExpensesByWorkspace(workspaceId: Long): Flow<List<Expense>> =
        expenseDao.getExpensesByWorkspace(workspaceId)

    suspend fun getAllExpensesOnce(): List<Expense> = 
        expenseDao.getAllExpensesOnce()

    fun getTotalSpending(workspaceId: Long): Flow<Double?> =
        expenseDao.getTotalSpendingByWorkspace(workspaceId)

    fun getCategoryTotals(workspaceId: Long): Flow<List<CategoryTotal>> =
        expenseDao.getCategoryTotals(workspaceId)

    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)

    // ─── Writes ───────────────────────────────────────────────────────────────

    suspend fun addExpense(expense: Expense): Long {
        val toInsert = expense.copy(
            syncStatus = SyncStatus.PENDING_CREATE,
            lastModified = System.currentTimeMillis()
        )
        return expenseDao.insertExpense(toInsert)
    }

    suspend fun updateExpense(expense: Expense) {
        val toUpdate = expense.copy(
            syncStatus = SyncStatus.PENDING_UPDATE,
            lastModified = System.currentTimeMillis()
        )
        expenseDao.updateExpense(toUpdate)
    }

    suspend fun deleteExpense(expense: Expense) {
        if (expense.firestoreId.isBlank()) {
            // Never synced — safe to hard-delete
            expenseDao.deleteExpense(expense)
        } else {
            // Has Firestore record — soft-delete
            expenseDao.markPendingDelete(expense.id)
        }
    }

    suspend fun deleteAllByWorkspace(workspaceId: Long) =
        expenseDao.deleteAllExpensesByWorkspace(workspaceId)
}
