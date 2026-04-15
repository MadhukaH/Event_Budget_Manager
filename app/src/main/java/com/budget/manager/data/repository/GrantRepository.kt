package com.budget.manager.data.repository

import com.budget.manager.data.local.dao.GrantDao
import com.budget.manager.data.model.GrantSettings
import com.budget.manager.data.model.SyncStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GrantRepository @Inject constructor(
    private val grantDao: GrantDao
) {
    fun getGrantSettings(): Flow<GrantSettings?> = grantDao.getGrantSettings()

    suspend fun setTotalGrant(amount: Double) {
        val existing = grantDao.getGrantSettingsOnce()
        val updated = (existing ?: GrantSettings()).copy(
            totalGrant = amount,
            syncStatus = SyncStatus.PENDING_UPDATE,
            lastModified = System.currentTimeMillis()
        )
        grantDao.upsertGrant(updated)
    }
}
