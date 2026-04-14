package com.budget.manager.ui.screens.dashboard;

import androidx.lifecycle.SavedStateHandle;
import com.budget.manager.data.repository.ExpenseRepository;
import com.budget.manager.data.repository.WorkspaceRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<WorkspaceRepository> workspaceRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public DashboardViewModel_Factory(Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.workspaceRepositoryProvider = workspaceRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(workspaceRepositoryProvider.get(), expenseRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static DashboardViewModel_Factory create(
      javax.inject.Provider<WorkspaceRepository> workspaceRepositoryProvider,
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new DashboardViewModel_Factory(Providers.asDaggerProvider(workspaceRepositoryProvider), Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static DashboardViewModel_Factory create(
      Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new DashboardViewModel_Factory(workspaceRepositoryProvider, expenseRepositoryProvider, savedStateHandleProvider);
  }

  public static DashboardViewModel newInstance(WorkspaceRepository workspaceRepository,
      ExpenseRepository expenseRepository, SavedStateHandle savedStateHandle) {
    return new DashboardViewModel(workspaceRepository, expenseRepository, savedStateHandle);
  }
}
