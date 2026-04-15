package com.budget.manager.data.local.dao

import androidx.room.*
import com.budget.manager.data.model.SyncStatus
import com.budget.manager.data.model.Workspace
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspaces WHERE syncStatus != 'PENDING_DELETE' ORDER BY createdAt DESC")
    fun getAllWorkspaces(): Flow<List<Workspace>>

    @Query("SELECT * FROM workspaces WHERE id = :id")
    fun getWorkspaceById(id: Long): Flow<Workspace?>

    @Query("SELECT * FROM workspaces WHERE id = :id")
    suspend fun getWorkspaceByIdAsync(id: Long): Workspace?

    @Query("SELECT * FROM workspaces WHERE firestoreId = :firestoreId LIMIT 1")
    suspend fun getWorkspaceByFirestoreId(firestoreId: String): Workspace?

    /** All records not yet in sync with Firestore */
    @Query("SELECT * FROM workspaces WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncWorkspaces(): List<Workspace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkspace(workspace: Workspace): Long

    @Update
    suspend fun updateWorkspace(workspace: Workspace)

    @Delete
    suspend fun deleteWorkspace(workspace: Workspace)

    @Query("DELETE FROM workspaces WHERE id = :id")
    suspend fun deleteWorkspaceById(id: Long)

    /**
     * Mark workspace as SYNCED.
     * The firestoreId was already set at creation — no need to update it here.
     * This simply clears the "dirty" flag.
     */
    @Query("UPDATE workspaces SET syncStatus = 'SYNCED' WHERE id = :localId")
    suspend fun markSynced(localId: Long)

    /** Soft-delete: hides from UI immediately, synced deletion happens in background */
    @Query("UPDATE workspaces SET syncStatus = 'PENDING_DELETE', lastModified = :timestamp WHERE id = :id")
    suspend fun markPendingDelete(id: Long, timestamp: Long = System.currentTimeMillis())
}
