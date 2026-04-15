package com.budget.manager.data.repository;

import com.budget.manager.data.local.dao.GrantDao;
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
public final class GrantRepository_Factory implements Factory<GrantRepository> {
  private final Provider<GrantDao> grantDaoProvider;

  public GrantRepository_Factory(Provider<GrantDao> grantDaoProvider) {
    this.grantDaoProvider = grantDaoProvider;
  }

  @Override
  public GrantRepository get() {
    return newInstance(grantDaoProvider.get());
  }

  public static GrantRepository_Factory create(javax.inject.Provider<GrantDao> grantDaoProvider) {
    return new GrantRepository_Factory(Providers.asDaggerProvider(grantDaoProvider));
  }

  public static GrantRepository_Factory create(Provider<GrantDao> grantDaoProvider) {
    return new GrantRepository_Factory(grantDaoProvider);
  }

  public static GrantRepository newInstance(GrantDao grantDao) {
    return new GrantRepository(grantDao);
  }
}
