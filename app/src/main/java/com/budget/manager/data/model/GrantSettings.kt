package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * GrantSettings — a single-row table that stores the global grant fund.
 * We always read/write row with id = 1.
 */
@Entity(tableName = "grant_settings")
data class GrantSettings(
    @PrimaryKey val id: Int = 1,
    val totalGrant: Double = 0.0,
    val firestoreId: String = "global_grant",
    val syncStatus: SyncStatus = SyncStatus.PENDING_CREATE,
    val lastModified: Long = System.currentTimeMillis()
)
