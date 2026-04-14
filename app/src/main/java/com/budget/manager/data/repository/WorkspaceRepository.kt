package com.budget.manager.data.repository

import com.budget.manager.data.local.dao.WorkspaceDao
import com.budget.manager.data.model.SyncStatus
import com.budget.manager.data.model.Workspace
import com.budget.manager.util.NetworkObserver
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * WorkspaceRepository — single source of truth for workspace data.
 *
 * Offline-first strategy:
 * - ALL reads come from Room (reactive Flow, instant updates)
 * - ALL writes go to Room first, with SyncStatus marking them dirty
 * - SyncManager / WorkManager handles pushing dirty records to Firestore
 *
 * This means UI is always fast (no waiting for network), and data is
 * eventually consistent with Firestore.
 */
@Singleton
class WorkspaceRepository @Inject constructor(
    private val workspaceDao: WorkspaceDao,
    private val networkObserver: NetworkObserver
) {
    // ─── Reads (Room as source of truth) ─────────────────────────────────────

    fun getAllWorkspaces(): Flow<List<Workspace>> = workspaceDao.getAllWorkspaces()

    fun getWorkspaceById(id: Long): Flow<Workspace?> = workspaceDao.getWorkspaceById(id)

    // ─── Writes (Room first, then flagged for sync) ───────────────────────────

    suspend fun createWorkspace(workspace: Workspace): Long {
        val toInsert = workspace.copy(
            syncStatus = SyncStatus.PENDING_CREATE,
            lastModified = System.currentTimeMillis()
        )
        return workspaceDao.insertWorkspace(toInsert)
    }

    suspend fun updateWorkspace(workspace: Workspace) {
        val toUpdate = workspace.copy(
            syncStatus = SyncStatus.PENDING_UPDATE,
            lastModified = System.currentTimeMillis()
        )
        workspaceDao.updateWorkspace(toUpdate)
    }

    /**
     * Soft-delete: marks workspace as PENDING_DELETE.
     * It will be excluded from queries immediately (DAO filters PENDING_DELETE),
     * and will be permanently removed from both Room and Firestore by SyncManager.
     */
    suspend fun deleteWorkspace(workspace: Workspace) {
        if (workspace.firestoreId.isBlank()) {
            // Never synced to Firestore — safe to hard-delete immediately
            workspaceDao.deleteWorkspace(workspace)
        } else {
            // Has Firestore record — soft-delete and let SyncManager handle it
            workspaceDao.markPendingDelete(workspace.id)
        }
    }

    suspend fun deleteWorkspaceById(id: Long) = workspaceDao.deleteWorkspaceById(id)
}
