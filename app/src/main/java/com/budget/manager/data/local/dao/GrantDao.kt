package com.budget.manager.data.local.dao

import androidx.room.*
import com.budget.manager.data.model.GrantSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface GrantDao {
    @Query("SELECT * FROM grant_settings WHERE id = 1 LIMIT 1")
    fun getGrantSettings(): Flow<GrantSettings?>

    @Query("SELECT * FROM grant_settings WHERE id = 1 LIMIT 1")
    suspend fun getGrantSettingsOnce(): GrantSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGrant(grantSettings: GrantSettings)

    @Query("UPDATE grant_settings SET syncStatus = 'SYNCED' WHERE id = 1")
    suspend fun markSynced()
}
