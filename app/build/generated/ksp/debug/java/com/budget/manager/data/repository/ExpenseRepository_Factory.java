package com.budget.manager.data.repository;

import com.budget.manager.data.local.dao.ExpenseCategoryDao;
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

  private final Provider<ExpenseCategoryDao> expenseCategoryDaoProvider;

  public ExpenseRepository_Factory(Provider<ExpenseDao> expenseDaoProvider,
      Provider<ExpenseCategoryDao> expenseCategoryDaoProvider) {
    this.expenseDaoProvider = expenseDaoProvider;
    this.expenseCategoryDaoProvider = expenseCategoryDaoProvider;
  }

  @Override
  public ExpenseRepository get() {
    return newInstance(expenseDaoProvider.get(), expenseCategoryDaoProvider.get());
  }

  public static ExpenseRepository_Factory create(
      javax.inject.Provider<ExpenseDao> expenseDaoProvider,
      javax.inject.Provider<ExpenseCategoryDao> expenseCategoryDaoProvider) {
    return new ExpenseRepository_Factory(Providers.asDaggerProvider(expenseDaoProvider), Providers.asDaggerProvider(expenseCategoryDaoProvider));
  }

  public static ExpenseRepository_Factory create(Provider<ExpenseDao> expenseDaoProvider,
      Provider<ExpenseCategoryDao> expenseCategoryDaoProvider) {
    return new ExpenseRepository_Factory(expenseDaoProvider, expenseCategoryDaoProvider);
  }

  public static ExpenseRepository newInstance(ExpenseDao expenseDao,
      ExpenseCategoryDao expenseCategoryDao) {
    return new ExpenseRepository(expenseDao, expenseCategoryDao);
  }
}
