package com.budget.manager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SyncStatus tracks the synchronization state of a record between
 * Room (local) and Firestore (remote).
 *
 * SYNCED         → exists in both Room and Firestore, no changes pending
 * PENDING_CREATE → created locally, not yet pushed to Firestore
 * PENDING_UPDATE → updated locally, needs to be pushed to Firestore
 * PENDING_DELETE → deleted locally, needs to be deleted from Firestore
 */
enum class SyncStatus {
    SYNCED,
    PENDING_CREATE,
    PENDING_UPDATE,
    PENDING_DELETE
}
