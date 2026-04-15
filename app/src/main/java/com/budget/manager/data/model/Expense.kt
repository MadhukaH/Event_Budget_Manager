package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Expense entity — a single budget line item inside a workspace.
 *
 * Same UUID-first strategy as [Workspace]:
 * [firestoreId] is a UUID generated locally at creation time, used as
 * the Firestore document ID. Sync is always a set() operation — idempotent.
 *
 * [workspaceFirestoreId] stores the parent workspace's UUID so the
 * Firestore path (workspaces/{id}/expenses/{id}) can be built without
 * an extra Room lookup during sync.
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

    // ── Sync metadata ──────────────────────────────────────────────────────
    val firestoreId: String = UUID.randomUUID().toString(),
    val workspaceFirestoreId: String = "",   // set by repository at creation
    val syncStatus: SyncStatus = SyncStatus.PENDING_CREATE,
    val lastModified: Long = System.currentTimeMillis()
)
