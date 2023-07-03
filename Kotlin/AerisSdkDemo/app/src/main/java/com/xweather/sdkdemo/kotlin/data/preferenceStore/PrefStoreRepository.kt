package com.xweather.sdkdemo.kotlin.data.preferenceStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PrefStoreRepository @Inject constructor(
    @ApplicationContext val context: Context,
) {

    private val STORE_NAME = "aeris_data_store"
    private val PREFS_NAME = "aeris_preference"
    private val prefStore: DataStore<Preferences>

    companion object {
        const val NOTIFICATION_ENABLED_KEY = "notification_enabled_key"
        const val UNIT_METRIC_ENABLED_KEY = "unit_metric_enabled_key"
        const val NTF_TIMESTAMP_KEY = "ntf_timestamp_key"
        const val LAST_FRAGMENT_KEY = "last_fragment_key"

        const val STRING_FLAG = "string_flag"
        const val LONG_FLAG = "long_flag"
    }

    init {
        prefStore = context.createDataStore(
            name = STORE_NAME,
            migrations = listOf(SharedPreferencesMigration(context, PREFS_NAME)),
        )
    }

    /*
     * Reified example - no delay
     */
    inline fun <reified T> get(key: String, defaultValue: T): T {
        return runBlocking {
            when (defaultValue) {
                is Boolean -> getBoolean(key).firstOrNull() ?: defaultValue
                is Int -> getInt(key).firstOrNull() ?: defaultValue
                is String -> getString(key).firstOrNull() ?: defaultValue
                is Long -> getLong(key).firstOrNull() ?: defaultValue
                else -> null
            } as T
        }
    }

    inline fun <reified T> set(key: String, value: T): Boolean {
        return runBlocking {
            when (value) {
                is Boolean -> {
                    setBoolean(key, value as Boolean)
                    true
                }
                is Int -> {
                    setInt(key, value as Int)
                    true
                }
                is String -> {
                    setString(key, value as String)
                    true
                }
                is Long -> {
                    setLong(key, value as Long)
                    true
                }
                else -> false
            }
        }
    }

    fun getBoolean(flag: String): Flow<Boolean?> {
        return prefStore.data.map { it[preferencesKey(flag)] }
    }

    suspend fun setBoolean(flag: String, isTrue: Boolean) {
        prefStore.edit {
            it[preferencesKey<Boolean>(flag)] = isTrue
        }
    }

    fun getString(flag: String): Flow<String> {
        return prefStore.data.map { it[preferencesKey(flag)] ?: "" }
    }

    suspend fun setString(flag: String, str: String) {
        prefStore.edit {
            it[preferencesKey<String>(flag)] = str
        }
    }

    fun getLong(flag: String): Flow<Long> {
        return prefStore.data.map { it[preferencesKey(flag)] ?: -1L }
    }

    suspend fun setLong(flag: String, num: Long) {
        prefStore.edit {
            it[preferencesKey<Long>(flag)] = num
        }
    }

    fun getInt(flag: String): Flow<Int?> {
        return prefStore.data.map { it[preferencesKey(flag)] }
    }

    suspend fun setInt(flag: String, num: Int) {
        prefStore.edit {
            it[preferencesKey<Int>(flag)] = num
        }
    }
}
