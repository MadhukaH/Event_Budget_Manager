package com.budget.manager.di;

import android.content.Context;
import com.budget.manager.data.local.BudgetDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideBudgetDatabaseFactory implements Factory<BudgetDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideBudgetDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BudgetDatabase get() {
    return provideBudgetDatabase(contextProvider.get());
  }

  public static AppModule_ProvideBudgetDatabaseFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new AppModule_ProvideBudgetDatabaseFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static AppModule_ProvideBudgetDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideBudgetDatabaseFactory(contextProvider);
  }

  public static BudgetDatabase provideBudgetDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBudgetDatabase(context));
  }
}
