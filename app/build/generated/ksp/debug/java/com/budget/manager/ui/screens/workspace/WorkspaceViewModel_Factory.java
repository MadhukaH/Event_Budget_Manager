package com.budget.manager.ui.screens.workspace;

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
public final class WorkspaceViewModel_Factory implements Factory<WorkspaceViewModel> {
  private final Provider<WorkspaceRepository> workspaceRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public WorkspaceViewModel_Factory(Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.workspaceRepositoryProvider = workspaceRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public WorkspaceViewModel get() {
    return newInstance(workspaceRepositoryProvider.get(), expenseRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static WorkspaceViewModel_Factory create(
      javax.inject.Provider<WorkspaceRepository> workspaceRepositoryProvider,
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new WorkspaceViewModel_Factory(Providers.asDaggerProvider(workspaceRepositoryProvider), Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static WorkspaceViewModel_Factory create(
      Provider<WorkspaceRepository> workspaceRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new WorkspaceViewModel_Factory(workspaceRepositoryProvider, expenseRepositoryProvider, savedStateHandleProvider);
  }

  public static WorkspaceViewModel newInstance(WorkspaceRepository workspaceRepository,
      ExpenseRepository expenseRepository, SavedStateHandle savedStateHandle) {
    return new WorkspaceViewModel(workspaceRepository, expenseRepository, savedStateHandle);
  }
}
