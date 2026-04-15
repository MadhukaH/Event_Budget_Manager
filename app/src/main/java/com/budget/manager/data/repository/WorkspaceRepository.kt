package com.budget.manager.data.repository

import com.budget.manager.data.local.dao.WorkspaceDao
import com.budget.manager.data.model.SyncStatus
import com.budget.manager.data.model.Workspace
import com.budget.manager.util.NetworkObserver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * WorkspaceRepository — single source of truth, offline-first.
 *
 * UUID-first guarantee:
 * Every new Workspace is assigned a [firestoreId] (UUID) before it is saved
 * to Room. This ID is used as the Firestore document ID on first sync.
 * Because the ID is stable, syncing the same workspace 100 times will always
 * result in exactly ONE document in Firestore.
 *
 * Write flow:
 * 1. Build Workspace with UUID firestoreId + SyncStatus.PENDING_CREATE
 * 2. Save to Room immediately → UI reacts instantly
 * 3. SyncManager picks it up and calls Firestore.set(firestoreId, data)
 * 4. SyncManager calls markSynced(localId) → status becomes SYNCED
 */
@Singleton
class WorkspaceRepository @Inject constructor(
    private val workspaceDao: WorkspaceDao,
    private val networkObserver: NetworkObserver
) {
    // ── Reads ─────────────────────────────────────────────────────────────────

    fun getAllWorkspaces(): Flow<List<Workspace>> = workspaceDao.getAllWorkspaces()

    fun getWorkspaceById(id: Long): Flow<Workspace?> = workspaceDao.getWorkspaceById(id)

    // ── Writes ────────────────────────────────────────────────────────────────

    /**
     * Creates a workspace locally.
     * The UUID [firestoreId] is assigned in the Workspace constructor default
     * parameter (UUID.randomUUID()). Caller does not need to supply it.
     */
    suspend fun createWorkspace(workspace: Workspace): Long {
        // Ensure it's tagged as pending — firestoreId is already a UUID
        val toInsert = workspace.copy(
            syncStatus = SyncStatus.PENDING_CREATE,
            lastModified = System.currentTimeMillis()
        )
        return workspaceDao.insertWorkspace(toInsert)
    }

    suspend fun updateWorkspace(workspace: Workspace) {
        workspaceDao.updateWorkspace(
            workspace.copy(
                syncStatus = SyncStatus.PENDING_UPDATE,
                lastModified = System.currentTimeMillis()
            )
        )
    }

    /**
     * Soft-delete: workspace is hidden from UI immediately.
     * If it was never synced (UUID exists only in Room), hard-delete it.
     * If it was synced, mark PENDING_DELETE so SyncManager removes it from
     * Firestore on next sync.
     */
    suspend fun deleteWorkspace(workspace: Workspace) {
        if (workspace.syncStatus == SyncStatus.PENDING_CREATE) {
            // Never reached Firestore — safe to hard-delete immediately
            workspaceDao.deleteWorkspace(workspace)
        } else {
            // Has a Firestore record — soft-delete and let SyncManager handle it
            workspaceDao.markPendingDelete(workspace.id)
        }
    }

    suspend fun deleteWorkspaceById(id: Long) = workspaceDao.deleteWorkspaceById(id)
}
