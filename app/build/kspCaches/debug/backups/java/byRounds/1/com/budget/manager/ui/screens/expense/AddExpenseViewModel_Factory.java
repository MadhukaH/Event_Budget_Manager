package com.budget.manager.ui.screens.expense;

import androidx.lifecycle.SavedStateHandle;
import com.budget.manager.data.repository.ExpenseRepository;
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

  public AddExpenseViewModel_Factory(Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddExpenseViewModel get() {
    return newInstance(expenseRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static AddExpenseViewModel_Factory create(
      javax.inject.Provider<ExpenseRepository> expenseRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddExpenseViewModel_Factory(Providers.asDaggerProvider(expenseRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static AddExpenseViewModel_Factory create(
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddExpenseViewModel_Factory(expenseRepositoryProvider, savedStateHandleProvider);
  }

  public static AddExpenseViewModel newInstance(ExpenseRepository expenseRepository,
      SavedStateHandle savedStateHandle) {
    return new AddExpenseViewModel(expenseRepository, savedStateHandle);
  }
}
