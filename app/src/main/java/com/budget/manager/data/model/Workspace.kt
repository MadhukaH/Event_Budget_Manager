package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Workspace entity — a budget environment for a single event or project.
 *
 * KEY DESIGN DECISION — UUID-first strategy:
 * The [firestoreId] is generated locally using UUID.randomUUID() at creation time,
 * BEFORE any network call. This UUID becomes the Firestore document ID.
 *
 * Why this prevents duplicates:
 * - Firestore uses document(firestoreId).set() → always overwrites the same doc
 * - WorkManager retries are safe — they write to the same document ID
 * - No "create if blank, update if not" branching needed
 * - Works correctly even if the app crashes mid-sync
 *
 * Sync state machine:
 * PENDING_CREATE → first upload to Firestore pending
 * PENDING_UPDATE → local change not yet pushed
 * PENDING_DELETE → deletion pending on Firestore
 * SYNCED         → Room and Firestore are in agreement
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

    // ── Sync metadata ──────────────────────────────────────────────────────
    // UUID generated locally at creation — used as Firestore document ID.
    // Never blank; always set before the record is saved to Room.
    val firestoreId: String = UUID.randomUUID().toString(),

    val syncStatus: SyncStatus = SyncStatus.PENDING_CREATE,
    val lastModified: Long = System.currentTimeMillis()
)
