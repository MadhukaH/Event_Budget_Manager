package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Workspace entity — a budget container for a single event or project.
 *
 * Sync fields:
 *  - firestoreId  : Firestore document ID (empty until synced)
 *  - syncStatus   : tracks offline-first state machine
 *  - lastModified : used to resolve conflicts (last-write-wins)
 */
@Entity(tableName = "workspaces")
data class Workspace(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val totalBudget: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),
    val colorIndex: Int = 0,

    // Sync metadata
    val firestoreId: String = "",
    val syncStatus: SyncStatus = SyncStatus.PENDING_CREATE,
    val lastModified: Long = System.currentTimeMillis()
)
