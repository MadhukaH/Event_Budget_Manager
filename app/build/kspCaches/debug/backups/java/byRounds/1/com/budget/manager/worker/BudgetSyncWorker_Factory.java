package com.budget.manager.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.budget.manager.data.sync.SyncManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class BudgetSyncWorker_Factory {
  private final Provider<SyncManager> syncManagerProvider;

  public BudgetSyncWorker_Factory(Provider<SyncManager> syncManagerProvider) {
    this.syncManagerProvider = syncManagerProvider;
  }

  public BudgetSyncWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, syncManagerProvider.get());
  }

  public static BudgetSyncWorker_Factory create(
      javax.inject.Provider<SyncManager> syncManagerProvider) {
    return new BudgetSyncWorker_Factory(Providers.asDaggerProvider(syncManagerProvider));
  }

  public static BudgetSyncWorker_Factory create(Provider<SyncManager> syncManagerProvider) {
    return new BudgetSyncWorker_Factory(syncManagerProvider);
  }

  public static BudgetSyncWorker newInstance(Context context, WorkerParameters workerParams,
      SyncManager syncManager) {
    return new BudgetSyncWorker(context, workerParams, syncManager);
  }
}
