package com.budget.manager.di;

import com.budget.manager.data.local.BudgetDatabase;
import com.budget.manager.data.local.dao.ExpenseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideExpenseDaoFactory implements Factory<ExpenseDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public AppModule_ProvideExpenseDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ExpenseDao get() {
    return provideExpenseDao(databaseProvider.get());
  }

  public static AppModule_ProvideExpenseDaoFactory create(
      javax.inject.Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideExpenseDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static AppModule_ProvideExpenseDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideExpenseDaoFactory(databaseProvider);
  }

  public static ExpenseDao provideExpenseDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideExpenseDao(database));
  }
}
