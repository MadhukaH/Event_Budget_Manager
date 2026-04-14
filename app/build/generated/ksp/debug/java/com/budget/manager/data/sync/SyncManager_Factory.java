package com.budget.manager.data.sync;

import com.budget.manager.data.local.dao.ExpenseDao;
import com.budget.manager.data.local.dao.WorkspaceDao;
import com.budget.manager.data.remote.FirestoreDataSource;
import com.budget.manager.util.NetworkObserver;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SyncManager_Factory implements Factory<SyncManager> {
  private final Provider<WorkspaceDao> workspaceDaoProvider;

  private final Provider<ExpenseDao> expenseDaoProvider;

  private final Provider<FirestoreDataSource> firestoreDataSourceProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  public SyncManager_Factory(Provider<WorkspaceDao> workspaceDaoProvider,
      Provider<ExpenseDao> expenseDaoProvider,
      Provider<FirestoreDataSource> firestoreDataSourceProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    this.workspaceDaoProvider = workspaceDaoProvider;
    this.expenseDaoProvider = expenseDaoProvider;
    this.firestoreDataSourceProvider = firestoreDataSourceProvider;
    this.networkObserverProvider = networkObserverProvider;
  }

  @Override
  public SyncManager get() {
    return newInstance(workspaceDaoProvider.get(), expenseDaoProvider.get(), firestoreDataSourceProvider.get(), networkObserverProvider.get());
  }

  public static SyncManager_Factory create(javax.inject.Provider<WorkspaceDao> workspaceDaoProvider,
      javax.inject.Provider<ExpenseDao> expenseDaoProvider,
      javax.inject.Provider<FirestoreDataSource> firestoreDataSourceProvider,
      javax.inject.Provider<NetworkObserver> networkObserverProvider) {
    return new SyncManager_Factory(Providers.asDaggerProvider(workspaceDaoProvider), Providers.asDaggerProvider(expenseDaoProvider), Providers.asDaggerProvider(firestoreDataSourceProvider), Providers.asDaggerProvider(networkObserverProvider));
  }

  public static SyncManager_Factory create(Provider<WorkspaceDao> workspaceDaoProvider,
      Provider<ExpenseDao> expenseDaoProvider,
      Provider<FirestoreDataSource> firestoreDataSourceProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    return new SyncManager_Factory(workspaceDaoProvider, expenseDaoProvider, firestoreDataSourceProvider, networkObserverProvider);
  }

  public static SyncManager newInstance(WorkspaceDao workspaceDao, ExpenseDao expenseDao,
      FirestoreDataSource firestoreDataSource, NetworkObserver networkObserver) {
    return new SyncManager(workspaceDao, expenseDao, firestoreDataSource, networkObserver);
  }
}
