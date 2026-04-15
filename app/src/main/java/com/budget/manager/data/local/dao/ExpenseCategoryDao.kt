package com.budget.manager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.budget.manager.data.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Query("SELECT * FROM custom_categories ORDER BY createdAt ASC")
    fun getAllCategories(): Flow<List<ExpenseCategory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: ExpenseCategory)
}
