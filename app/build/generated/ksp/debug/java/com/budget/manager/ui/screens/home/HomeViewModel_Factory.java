package com.budget.manager.ui.screens.home;

import androidx.work.WorkManager;
import com.budget.manager.data.repository.ExpenseRepository;
import com.budget.manager.data.repository.WorkspaceRepository;
import com.budget.manager.util.NetworkObserver;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<WorkspaceRepository> workspaceRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  private final Provider<WorkManager> workManagerProvider;

  public HomeViewModel_Factory(Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<NetworkObserver> networkObserverProvider,
      Provider<WorkManager> workManagerProvider) {
    this.workspaceRepositoryProvider = workspaceRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.networkObserverProvider = networkObserverProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(workspaceRepositoryProvider.get(), expenseRepositoryProvider.get(), networkObserverProvider.get(), workManagerProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<WorkspaceRepository> workspaceRepositoryProvider,
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<NetworkObserver> networkObserverProvider,
      javax.inject.Provider<WorkManager> workManagerProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(workspaceRepositoryProvider), Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(networkObserverProvider), Providers.asDaggerProvider(workManagerProvider));
  }

  public static HomeViewModel_Factory create(
      Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<NetworkObserver> networkObserverProvider,
      Provider<WorkManager> workManagerProvider) {
    return new HomeViewModel_Factory(workspaceRepositoryProvider, expenseRepositoryProvider, networkObserverProvider, workManagerProvider);
  }

  public static HomeViewModel newInstance(WorkspaceRepository workspaceRepository,
      ExpenseRepository expenseRepository, NetworkObserver networkObserver,
      WorkManager workManager) {
    return new HomeViewModel(workspaceRepository, expenseRepository, networkObserver, workManager);
  }
}
