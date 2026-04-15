package com.budget.manager.di;

import com.budget.manager.data.local.BudgetDatabase;
import com.budget.manager.data.local.dao.ExpenseCategoryDao;
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
public final class AppModule_ProvideExpenseCategoryDaoFactory implements Factory<ExpenseCategoryDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public AppModule_ProvideExpenseCategoryDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ExpenseCategoryDao get() {
    return provideExpenseCategoryDao(databaseProvider.get());
  }

  public static AppModule_ProvideExpenseCategoryDaoFactory create(
      javax.inject.Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideExpenseCategoryDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static AppModule_ProvideExpenseCategoryDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideExpenseCategoryDaoFactory(databaseProvider);
  }

  public static ExpenseCategoryDao provideExpenseCategoryDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideExpenseCategoryDao(database));
  }
}
