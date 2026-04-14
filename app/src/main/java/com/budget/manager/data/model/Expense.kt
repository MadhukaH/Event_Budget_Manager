package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Expense entity — a single budget line item inside a workspace.
 *
 * Sync fields:
 *  - firestoreId      : Firestore document ID
 *  - workspaceFirestoreId : the parent workspace's Firestore ID (needed for Firestore path)
 *  - syncStatus       : offline state machine
 *  - lastModified     : conflict resolution timestamp
 */
@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Workspace::class,
            parentColumns = ["id"],
            childColumns = ["workspaceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workspaceId"),
        Index("syncStatus"),
        Index("firestoreId")
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workspaceId: Long,
    val category: String,
    val amount: Double,
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis(),

    // Sync metadata
    val firestoreId: String = "",
    val workspaceFirestoreId: String = "",
    val syncStatus: SyncStatus = SyncStatus.PENDING_CREATE,
    val lastModified: Long = System.currentTimeMillis()
)
