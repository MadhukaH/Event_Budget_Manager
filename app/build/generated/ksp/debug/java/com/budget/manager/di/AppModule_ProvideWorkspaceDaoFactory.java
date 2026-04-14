package com.budget.manager.di;

import com.budget.manager.data.local.BudgetDatabase;
import com.budget.manager.data.local.dao.WorkspaceDao;
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
public final class AppModule_ProvideWorkspaceDaoFactory implements Factory<WorkspaceDao> {
  private final Provider<BudgetDatabase> databaseProvider;

  public AppModule_ProvideWorkspaceDaoFactory(Provider<BudgetDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WorkspaceDao get() {
    return provideWorkspaceDao(databaseProvider.get());
  }

  public static AppModule_ProvideWorkspaceDaoFactory create(
      javax.inject.Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideWorkspaceDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static AppModule_ProvideWorkspaceDaoFactory create(
      Provider<BudgetDatabase> databaseProvider) {
    return new AppModule_ProvideWorkspaceDaoFactory(databaseProvider);
  }

  public static WorkspaceDao provideWorkspaceDao(BudgetDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWorkspaceDao(database));
  }
}
