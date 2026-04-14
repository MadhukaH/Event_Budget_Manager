package com.budget.manager.data.repository;

import com.budget.manager.data.local.dao.WorkspaceDao;
import com.budget.manager.util.NetworkObserver;
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
public final class WorkspaceRepository_Factory implements Factory<WorkspaceRepository> {
  private final Provider<WorkspaceDao> workspaceDaoProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  public WorkspaceRepository_Factory(Provider<WorkspaceDao> workspaceDaoProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    this.workspaceDaoProvider = workspaceDaoProvider;
    this.networkObserverProvider = networkObserverProvider;
  }

  @Override
  public WorkspaceRepository get() {
    return newInstance(workspaceDaoProvider.get(), networkObserverProvider.get());
  }

  public static WorkspaceRepository_Factory create(
      javax.inject.Provider<WorkspaceDao> workspaceDaoProvider,
      javax.inject.Provider<NetworkObserver> networkObserverProvider) {
    return new WorkspaceRepository_Factory(Providers.asDaggerProvider(workspaceDaoProvider), Providers.asDaggerProvider(networkObserverProvider));
  }

  public static WorkspaceRepository_Factory create(Provider<WorkspaceDao> workspaceDaoProvider,
      Provider<NetworkObserver> networkObserverProvider) {
    return new WorkspaceRepository_Factory(workspaceDaoProvider, networkObserverProvider);
  }

  public static WorkspaceRepository newInstance(WorkspaceDao workspaceDao,
      NetworkObserver networkObserver) {
    return new WorkspaceRepository(workspaceDao, networkObserver);
  }
}
