package com.budget.manager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.budget.manager.worker.BudgetSyncWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BudgetApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
        schedulePeriodicsSync()
    }

    /**
     * Registers the periodic background sync worker.
     * KEEP policy means existing schedule is preserved — prevents scheduling
     * a new worker on every app launch.
     */
    private fun schedulePeriodicsSync() {
        workManager.enqueueUniquePeriodicWork(
            BudgetSyncWorker.WORK_NAME_PERIODIC,
            ExistingPeriodicWorkPolicy.KEEP,
            BudgetSyncWorker.periodicWorkRequest()
        )
    }
}
