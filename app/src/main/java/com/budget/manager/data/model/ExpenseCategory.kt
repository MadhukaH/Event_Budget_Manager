package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_categories")
data class ExpenseCategory(
    @PrimaryKey
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
