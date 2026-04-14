package com.budget.manager.data.repository;

import com.budget.manager.data.local.dao.ExpenseDao;
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
public final class ExpenseRepository_Factory implements Factory<ExpenseRepository> {
  private final Provider<ExpenseDao> expenseDaoProvider;

  public ExpenseRepository_Factory(Provider<ExpenseDao> expenseDaoProvider) {
    this.expenseDaoProvider = expenseDaoProvider;
  }

  @Override
  public ExpenseRepository get() {
    return newInstance(expenseDaoProvider.get());
  }

  public static ExpenseRepository_Factory create(
      javax.inject.Provider<ExpenseDao> expenseDaoProvider) {
    return new ExpenseRepository_Factory(Providers.asDaggerProvider(expenseDaoProvider));
  }

  public static ExpenseRepository_Factory create(Provider<ExpenseDao> expenseDaoProvider) {
    return new ExpenseRepository_Factory(expenseDaoProvider);
  }

  public static ExpenseRepository newInstance(ExpenseDao expenseDao) {
    return new ExpenseRepository(expenseDao);
  }
}
