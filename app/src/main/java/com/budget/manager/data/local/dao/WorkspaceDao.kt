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

    @Query("SELECT * FROM workspaces WHERE firestoreId = :firestoreId LIMIT 1")
    suspend fun getWorkspaceByFirestoreId(firestoreId: String): Workspace?

    /** Returns all records that need to be pushed to Firestore */
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

    /** Mark a workspace as synced and store its Firestore document ID */
    @Query("UPDATE workspaces SET syncStatus = 'SYNCED', firestoreId = :firestoreId WHERE id = :localId")
    suspend fun markSynced(localId: Long, firestoreId: String)

    /** Soft-delete: mark for deletion, will be removed from Firestore on next sync */
    @Query("UPDATE workspaces SET syncStatus = 'PENDING_DELETE' WHERE id = :id")
    suspend fun markPendingDelete(id: Long)
}
