package com.budget.manager.ui.screens.workspace;

import androidx.lifecycle.SavedStateHandle;
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
public final class WorkspaceViewModel_Factory implements Factory<WorkspaceViewModel> {
  private final Provider<WorkspaceRepository> workspaceRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<WorkManager> workManagerProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  public WorkspaceViewModel_Factory(Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    this.workspaceRepositoryProvider = workspaceRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.workManagerProvider = workManagerProvider;
    this.networkObserverProvider = networkObserverProvider;
  }

  @Override
  public WorkspaceViewModel get() {
    return newInstance(workspaceRepositoryProvider.get(), expenseRepositoryProvider.get(), savedStateHandleProvider.get(), workManagerProvider.get(), networkObserverProvider.get());
  }

  public static WorkspaceViewModel_Factory create(
      javax.inject.Provider<WorkspaceRepository> workspaceRepositoryProvider,
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<WorkManager> workManagerProvider,
      javax.inject.Provider<NetworkObserver> networkObserverProvider) {
    return new WorkspaceViewModel_Factory(Providers.asDaggerProvider(workspaceRepositoryProvider), Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(workManagerProvider), Providers.asDaggerProvider(networkObserverProvider));
  }

  public static WorkspaceViewModel_Factory create(
      Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    return new WorkspaceViewModel_Factory(workspaceRepositoryProvider, expenseRepositoryProvider, savedStateHandleProvider, workManagerProvider, networkObserverProvider);
  }

  public static WorkspaceViewModel newInstance(WorkspaceRepository workspaceRepository,
      ExpenseRepository expenseRepository, SavedStateHandle savedStateHandle,
      WorkManager workManager, NetworkObserver networkObserver) {
    return new WorkspaceViewModel(workspaceRepository, expenseRepository, savedStateHandle, workManager, networkObserver);
  }
}
