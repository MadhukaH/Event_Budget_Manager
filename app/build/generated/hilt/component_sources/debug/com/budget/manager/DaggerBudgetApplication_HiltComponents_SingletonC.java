package com.budget.manager;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;
import com.budget.manager.data.local.BudgetDatabase;
import com.budget.manager.data.local.dao.ExpenseCategoryDao;
import com.budget.manager.data.local.dao.ExpenseDao;
import com.budget.manager.data.local.dao.GrantDao;
import com.budget.manager.data.local.dao.WorkspaceDao;
import com.budget.manager.data.remote.FirestoreDataSource;
import com.budget.manager.data.repository.ExpenseRepository;
import com.budget.manager.data.repository.GrantRepository;
import com.budget.manager.data.repository.WorkspaceRepository;
import com.budget.manager.data.sync.SyncManager;
import com.budget.manager.di.AppModule_ProvideBudgetDatabaseFactory;
import com.budget.manager.di.AppModule_ProvideExpenseCategoryDaoFactory;
import com.budget.manager.di.AppModule_ProvideExpenseDaoFactory;
import com.budget.manager.di.AppModule_ProvideFirebaseFirestoreFactory;
import com.budget.manager.di.AppModule_ProvideGrantDaoFactory;
import com.budget.manager.di.AppModule_ProvideWorkManagerFactory;
import com.budget.manager.di.AppModule_ProvideWorkspaceDaoFactory;
import com.budget.manager.ui.screens.dashboard.DashboardViewModel;
import com.budget.manager.ui.screens.dashboard.DashboardViewModel_HiltModules;
import com.budget.manager.ui.screens.dashboard.DashboardViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.budget.manager.ui.screens.dashboard.DashboardViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.budget.manager.ui.screens.expense.AddExpenseViewModel;
import com.budget.manager.ui.screens.expense.AddExpenseViewModel_HiltModules;
import com.budget.manager.ui.screens.expense.AddExpenseViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.budget.manager.ui.screens.expense.AddExpenseViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.budget.manager.ui.screens.home.HomeViewModel;
import com.budget.manager.ui.screens.home.HomeViewModel_HiltModules;
import com.budget.manager.ui.screens.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.budget.manager.ui.screens.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.budget.manager.ui.screens.workspace.WorkspaceViewModel;
import com.budget.manager.ui.screens.workspace.WorkspaceViewModel_HiltModules;
import com.budget.manager.ui.screens.workspace.WorkspaceViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.budget.manager.ui.screens.workspace.WorkspaceViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.budget.manager.util.NetworkObserver;
import com.budget.manager.worker.BudgetSyncWorker;
import com.budget.manager.worker.BudgetSyncWorker_AssistedFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerBudgetApplication_HiltComponents_SingletonC {
  private DaggerBudgetApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public BudgetApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements BudgetApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements BudgetApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements BudgetApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements BudgetApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements BudgetApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements BudgetApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements BudgetApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public BudgetApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends BudgetApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends BudgetApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends BudgetApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends BudgetApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>of(AddExpenseViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AddExpenseViewModel_HiltModules.KeyModule.provide(), DashboardViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, DashboardViewModel_HiltModules.KeyModule.provide(), HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide(), WorkspaceViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, WorkspaceViewModel_HiltModules.KeyModule.provide()));
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends BudgetApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddExpenseViewModel> addExpenseViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<WorkspaceViewModel> workspaceViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addExpenseViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.workspaceViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>of(AddExpenseViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) addExpenseViewModelProvider), DashboardViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) dashboardViewModelProvider), HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) homeViewModelProvider), WorkspaceViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) workspaceViewModelProvider)));
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.budget.manager.ui.screens.expense.AddExpenseViewModel 
          return (T) new AddExpenseViewModel(singletonCImpl.expenseRepositoryProvider.get(), viewModelCImpl.savedStateHandle, singletonCImpl.provideWorkManagerProvider.get(), singletonCImpl.networkObserverProvider.get());

          case 1: // com.budget.manager.ui.screens.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.workspaceRepositoryProvider.get(), singletonCImpl.expenseRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          case 2: // com.budget.manager.ui.screens.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.workspaceRepositoryProvider.get(), singletonCImpl.expenseRepositoryProvider.get(), singletonCImpl.grantRepositoryProvider.get(), singletonCImpl.networkObserverProvider.get(), singletonCImpl.provideWorkManagerProvider.get());

          case 3: // com.budget.manager.ui.screens.workspace.WorkspaceViewModel 
          return (T) new WorkspaceViewModel(singletonCImpl.workspaceRepositoryProvider.get(), singletonCImpl.expenseRepositoryProvider.get(), viewModelCImpl.savedStateHandle, singletonCImpl.provideWorkManagerProvider.get(), singletonCImpl.networkObserverProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends BudgetApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends BudgetApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends BudgetApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<BudgetDatabase> provideBudgetDatabaseProvider;

    private Provider<WorkspaceDao> provideWorkspaceDaoProvider;

    private Provider<ExpenseDao> provideExpenseDaoProvider;

    private Provider<GrantDao> provideGrantDaoProvider;

    private Provider<FirebaseFirestore> provideFirebaseFirestoreProvider;

    private Provider<FirestoreDataSource> firestoreDataSourceProvider;

    private Provider<NetworkObserver> networkObserverProvider;

    private Provider<SyncManager> syncManagerProvider;

    private Provider<BudgetSyncWorker_AssistedFactory> budgetSyncWorker_AssistedFactoryProvider;

    private Provider<WorkManager> provideWorkManagerProvider;

    private Provider<ExpenseCategoryDao> provideExpenseCategoryDaoProvider;

    private Provider<ExpenseRepository> expenseRepositoryProvider;

    private Provider<WorkspaceRepository> workspaceRepositoryProvider;

    private Provider<GrantRepository> grantRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return ImmutableMap.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>of("com.budget.manager.worker.BudgetSyncWorker", ((Provider) budgetSyncWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideBudgetDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<BudgetDatabase>(singletonCImpl, 3));
      this.provideWorkspaceDaoProvider = DoubleCheck.provider(new SwitchingProvider<WorkspaceDao>(singletonCImpl, 2));
      this.provideExpenseDaoProvider = DoubleCheck.provider(new SwitchingProvider<ExpenseDao>(singletonCImpl, 4));
      this.provideGrantDaoProvider = DoubleCheck.provider(new SwitchingProvider<GrantDao>(singletonCImpl, 5));
      this.provideFirebaseFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 7));
      this.firestoreDataSourceProvider = DoubleCheck.provider(new SwitchingProvider<FirestoreDataSource>(singletonCImpl, 6));
      this.networkObserverProvider = DoubleCheck.provider(new SwitchingProvider<NetworkObserver>(singletonCImpl, 8));
      this.syncManagerProvider = DoubleCheck.provider(new SwitchingProvider<SyncManager>(singletonCImpl, 1));
      this.budgetSyncWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<BudgetSyncWorker_AssistedFactory>(singletonCImpl, 0));
      this.provideWorkManagerProvider = DoubleCheck.provider(new SwitchingProvider<WorkManager>(singletonCImpl, 9));
      this.provideExpenseCategoryDaoProvider = DoubleCheck.provider(new SwitchingProvider<ExpenseCategoryDao>(singletonCImpl, 11));
      this.expenseRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ExpenseRepository>(singletonCImpl, 10));
      this.workspaceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<WorkspaceRepository>(singletonCImpl, 12));
      this.grantRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<GrantRepository>(singletonCImpl, 13));
    }

    @Override
    public void injectBudgetApplication(BudgetApplication budgetApplication) {
      injectBudgetApplication2(budgetApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private BudgetApplication injectBudgetApplication2(BudgetApplication instance) {
      BudgetApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      BudgetApplication_MembersInjector.injectWorkManager(instance, provideWorkManagerProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.budget.manager.worker.BudgetSyncWorker_AssistedFactory 
          return (T) new BudgetSyncWorker_AssistedFactory() {
            @Override
            public BudgetSyncWorker create(Context context, WorkerParameters workerParams) {
              return new BudgetSyncWorker(context, workerParams, singletonCImpl.syncManagerProvider.get());
            }
          };

          case 1: // com.budget.manager.data.sync.SyncManager 
          return (T) new SyncManager(singletonCImpl.provideWorkspaceDaoProvider.get(), singletonCImpl.provideExpenseDaoProvider.get(), singletonCImpl.provideGrantDaoProvider.get(), singletonCImpl.firestoreDataSourceProvider.get(), singletonCImpl.networkObserverProvider.get());

          case 2: // com.budget.manager.data.local.dao.WorkspaceDao 
          return (T) AppModule_ProvideWorkspaceDaoFactory.provideWorkspaceDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 3: // com.budget.manager.data.local.BudgetDatabase 
          return (T) AppModule_ProvideBudgetDatabaseFactory.provideBudgetDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.budget.manager.data.local.dao.ExpenseDao 
          return (T) AppModule_ProvideExpenseDaoFactory.provideExpenseDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 5: // com.budget.manager.data.local.dao.GrantDao 
          return (T) AppModule_ProvideGrantDaoFactory.provideGrantDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 6: // com.budget.manager.data.remote.FirestoreDataSource 
          return (T) new FirestoreDataSource(singletonCImpl.provideFirebaseFirestoreProvider.get());

          case 7: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) AppModule_ProvideFirebaseFirestoreFactory.provideFirebaseFirestore();

          case 8: // com.budget.manager.util.NetworkObserver 
          return (T) new NetworkObserver(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // androidx.work.WorkManager 
          return (T) AppModule_ProvideWorkManagerFactory.provideWorkManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.budget.manager.data.repository.ExpenseRepository 
          return (T) new ExpenseRepository(singletonCImpl.provideExpenseDaoProvider.get(), singletonCImpl.provideExpenseCategoryDaoProvider.get());

          case 11: // com.budget.manager.data.local.dao.ExpenseCategoryDao 
          return (T) AppModule_ProvideExpenseCategoryDaoFactory.provideExpenseCategoryDao(singletonCImpl.provideBudgetDatabaseProvider.get());

          case 12: // com.budget.manager.data.repository.WorkspaceRepository 
          return (T) new WorkspaceRepository(singletonCImpl.provideWorkspaceDaoProvider.get(), singletonCImpl.networkObserverProvider.get());

          case 13: // com.budget.manager.data.repository.GrantRepository 
          return (T) new GrantRepository(singletonCImpl.provideGrantDaoProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
