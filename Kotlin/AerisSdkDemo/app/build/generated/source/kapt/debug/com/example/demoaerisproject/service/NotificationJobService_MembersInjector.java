// Generated by Dagger (https://dagger.dev).
package com.example.demoaerisproject.service;

import com.example.demoaerisproject.data.preferenceStore.PrefStoreRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.data.weather.WeatherRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NotificationJobService_MembersInjector implements MembersInjector<NotificationJobService> {
  private final Provider<MyPlaceRepository> myPlaceRepositoryProvider;

  private final Provider<PrefStoreRepository> prefStoreRepositoryProvider;

  private final Provider<WeatherRepository> weatherRepositoryProvider;

  private final Provider<PrefStoreRepository> prefStoreProvider;

  public NotificationJobService_MembersInjector(
      Provider<MyPlaceRepository> myPlaceRepositoryProvider,
      Provider<PrefStoreRepository> prefStoreRepositoryProvider,
      Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<PrefStoreRepository> prefStoreProvider) {
    this.myPlaceRepositoryProvider = myPlaceRepositoryProvider;
    this.prefStoreRepositoryProvider = prefStoreRepositoryProvider;
    this.weatherRepositoryProvider = weatherRepositoryProvider;
    this.prefStoreProvider = prefStoreProvider;
  }

  public static MembersInjector<NotificationJobService> create(
      Provider<MyPlaceRepository> myPlaceRepositoryProvider,
      Provider<PrefStoreRepository> prefStoreRepositoryProvider,
      Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<PrefStoreRepository> prefStoreProvider) {
    return new NotificationJobService_MembersInjector(myPlaceRepositoryProvider, prefStoreRepositoryProvider, weatherRepositoryProvider, prefStoreProvider);
  }

  @Override
  public void injectMembers(NotificationJobService instance) {
    injectMyPlaceRepository(instance, myPlaceRepositoryProvider.get());
    injectPrefStoreRepository(instance, prefStoreRepositoryProvider.get());
    injectWeatherRepository(instance, weatherRepositoryProvider.get());
    injectPrefStore(instance, prefStoreProvider.get());
  }

  @InjectedFieldSignature("com.example.demoaerisproject.service.NotificationJobService.myPlaceRepository")
  public static void injectMyPlaceRepository(NotificationJobService instance,
      MyPlaceRepository myPlaceRepository) {
    instance.myPlaceRepository = myPlaceRepository;
  }

  @InjectedFieldSignature("com.example.demoaerisproject.service.NotificationJobService.prefStoreRepository")
  public static void injectPrefStoreRepository(NotificationJobService instance,
      PrefStoreRepository prefStoreRepository) {
    instance.prefStoreRepository = prefStoreRepository;
  }

  @InjectedFieldSignature("com.example.demoaerisproject.service.NotificationJobService.weatherRepository")
  public static void injectWeatherRepository(NotificationJobService instance,
      WeatherRepository weatherRepository) {
    instance.weatherRepository = weatherRepository;
  }

  @InjectedFieldSignature("com.example.demoaerisproject.service.NotificationJobService.prefStore")
  public static void injectPrefStore(NotificationJobService instance,
      PrefStoreRepository prefStore) {
    instance.prefStore = prefStore;
  }
}