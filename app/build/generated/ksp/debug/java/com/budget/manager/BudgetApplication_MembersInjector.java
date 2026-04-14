package com.budget.manager;

import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.WorkManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class BudgetApplication_MembersInjector implements MembersInjector<BudgetApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<WorkManager> workManagerProvider;

  public BudgetApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<WorkManager> workManagerProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.workManagerProvider = workManagerProvider;
  }

  public static MembersInjector<BudgetApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<WorkManager> workManagerProvider) {
    return new BudgetApplication_MembersInjector(workerFactoryProvider, workManagerProvider);
  }

  public static MembersInjector<BudgetApplication> create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider,
      javax.inject.Provider<WorkManager> workManagerProvider) {
    return new BudgetApplication_MembersInjector(Providers.asDaggerProvider(workerFactoryProvider), Providers.asDaggerProvider(workManagerProvider));
  }

  @Override
  public void injectMembers(BudgetApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectWorkManager(instance, workManagerProvider.get());
  }

  @InjectedFieldSignature("com.budget.manager.BudgetApplication.workerFactory")
  public static void injectWorkerFactory(BudgetApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.budget.manager.BudgetApplication.workManager")
  public static void injectWorkManager(BudgetApplication instance, WorkManager workManager) {
    instance.workManager = workManager;
  }
}
