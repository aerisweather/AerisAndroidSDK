// Generated by Dagger (https://dagger.dev).
package com.example.demoaerisproject.di;

import com.example.demoaerisproject.data.location.LocationRepository;
import com.example.demoaerisproject.data.room.MyPlaceRepository;
import com.example.demoaerisproject.view.locations.LocationSearchViewModel;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LocationModule_ProvideLocationSearchViewModelFactory implements Factory<LocationSearchViewModel> {
  private final Provider<LocationRepository> locationRepositoryProvider;

  private final Provider<MyPlaceRepository> myPlaceRepositoryProvider;

  public LocationModule_ProvideLocationSearchViewModelFactory(
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<MyPlaceRepository> myPlaceRepositoryProvider) {
    this.locationRepositoryProvider = locationRepositoryProvider;
    this.myPlaceRepositoryProvider = myPlaceRepositoryProvider;
  }

  @Override
  public LocationSearchViewModel get() {
    return provideLocationSearchViewModel(locationRepositoryProvider.get(), myPlaceRepositoryProvider.get());
  }

  public static LocationModule_ProvideLocationSearchViewModelFactory create(
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<MyPlaceRepository> myPlaceRepositoryProvider) {
    return new LocationModule_ProvideLocationSearchViewModelFactory(locationRepositoryProvider, myPlaceRepositoryProvider);
  }

  public static LocationSearchViewModel provideLocationSearchViewModel(
      LocationRepository locationRepository, MyPlaceRepository myPlaceRepository) {
    return Preconditions.checkNotNullFromProvides(LocationModule.INSTANCE.provideLocationSearchViewModel(locationRepository, myPlaceRepository));
  }
}