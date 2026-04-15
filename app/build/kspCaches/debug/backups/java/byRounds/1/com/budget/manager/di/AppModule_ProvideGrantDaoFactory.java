package com.budget.manager.di;

import com.budget.manager.data.local.BudgetDatabase;
import com.budget.manager.data.local.dao.GrantDao;
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
public final class AppModule_ProvideGrantDaoFactory implements Factory<GrantDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public AppModule_ProvideGrantDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public GrantDao get() {
    return provideGrantDao(databaseProvider.get());
  }

  public static AppModule_ProvideGrantDaoFactory create(
      javax.inject.Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideGrantDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static AppModule_ProvideGrantDaoFactory create(Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideGrantDaoFactory(databaseProvider);
  }

  public static GrantDao provideGrantDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGrantDao(database));
  }
}
