package com.budget.manager.ui.screens.expense;

import androidx.lifecycle.SavedStateHandle;
import androidx.work.WorkManager;
import com.budget.manager.data.repository.ExpenseRepository;
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
public final class AddExpenseViewModel_Factory implements Factory<AddExpenseViewModel> {
  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<WorkManager> workManagerProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  public AddExpenseViewModel_Factory(Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.workManagerProvider = workManagerProvider;
    this.networkObserverProvider = networkObserverProvider;
  }

  @Override
  public AddExpenseViewModel get() {
    return newInstance(expenseRepositoryProvider.get(), savedStateHandleProvider.get(), workManagerProvider.get(), networkObserverProvider.get());
  }

  public static AddExpenseViewModel_Factory create(
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<WorkManager> workManagerProvider,
      javax.inject.Provider<NetworkObserver> networkObserverProvider) {
    return new AddExpenseViewModel_Factory(Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(workManagerProvider), Providers.asDaggerProvider(networkObserverProvider));
  }

  public static AddExpenseViewModel_Factory create(
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<WorkManager> workManagerProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    return new AddExpenseViewModel_Factory(expenseRepositoryProvider, savedStateHandleProvider, workManagerProvider, networkObserverProvider);
  }

  public static AddExpenseViewModel newInstance(ExpenseRepository expenseRepository,
      SavedStateHandle savedStateHandle, WorkManager workManager, NetworkObserver networkObserver) {
    return new AddExpenseViewModel(expenseRepository, savedStateHandle, workManager, networkObserver);
  }
}
