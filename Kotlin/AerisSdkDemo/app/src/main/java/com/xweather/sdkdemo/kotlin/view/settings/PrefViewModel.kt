package com.xweather.sdkdemo.kotlin.view.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.xweather.sdkdemo.kotlin.data.preferenceStore.PrefStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrefViewModel @Inject constructor(
    private val prefStoreRepository: PrefStoreRepository,
) : ViewModel() {

    val isNotificationEnabled: LiveData<Boolean?> =
        prefStoreRepository.getBoolean(PrefStoreRepository.NOTIFICATION_ENABLED_KEY)
            .asLiveData()

    fun setNotificationEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            prefStoreRepository.setBoolean(PrefStoreRepository.NOTIFICATION_ENABLED_KEY, isEnabled)
        }
    }

    val isMetric: LiveData<Boolean?> =
        prefStoreRepository.getBoolean(PrefStoreRepository.UNIT_METRIC_ENABLED_KEY)
            .asLiveData()

    fun setMetric(isEnabled: Boolean) {
        viewModelScope.launch {
            prefStoreRepository.setBoolean(PrefStoreRepository.UNIT_METRIC_ENABLED_KEY, isEnabled)
        }
    }
}
