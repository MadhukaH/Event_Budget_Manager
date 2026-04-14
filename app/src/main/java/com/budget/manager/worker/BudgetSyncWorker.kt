package com.budget.manager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.budget.manager.data.sync.SyncManager
import com.budget.manager.data.sync.SyncResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

private const val TAG = "BudgetSyncWorker"

/**
 * BudgetSyncWorker — runs in the background via WorkManager.
 *
 * Triggered:
 *  - Periodically (every 15 minutes when on network)
 *  - On-demand when app detects network restored
 *  - On app startup for any pending changes
 *
 * Uses [HiltWorker] for dependency injection into WorkManager.
 * Retries up to 3 times with exponential backoff on failure.
 */
@HiltWorker
class BudgetSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncManager: SyncManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: starting sync (attempt ${runAttemptCount + 1})")

        return when (val result = syncManager.syncAll()) {
            is SyncResult.Success -> {
                Log.d(TAG, "doWork: sync succeeded")
                Result.success()
            }
            is SyncResult.NoNetwork -> {
                Log.d(TAG, "doWork: no network, will retry when network available")
                Result.retry()
            }
            is SyncResult.Error -> {
                Log.e(TAG, "doWork: sync error — ${result.message}")
                if (runAttemptCount < 3) Result.retry() else Result.failure()
            }
        }
    }

    companion object {
        const val WORK_NAME_PERIODIC = "budget_sync_periodic"
        const val WORK_NAME_ONE_TIME = "budget_sync_immediate"

        /**
         * Periodic sync: runs every 15 minutes when connected.
         * WorkManager handles scheduling, battery optimization, and doze mode.
         */
        fun periodicWorkRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(false)
                .build()

            return PeriodicWorkRequestBuilder<BudgetSyncWorker>(
                repeatInterval = 15,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        }

        /**
         * Immediate one-time sync: triggered when network comes back online
         * or when the user explicitly requests a sync.
         */
        fun oneTimeWorkRequest(): OneTimeWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            return OneTimeWorkRequestBuilder<BudgetSyncWorker>()
                .setConstraints(constraints)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        }
    }
}
