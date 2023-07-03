package com.xweather.sdkdemo.kotlin.data.preferenceStore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrefStoreRepositoryTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        Assert.assertEquals("com.xweather.sdkdemo.kotlin", appContext.packageName)
//    }

    @Test
    fun happy_path_insert_retrieve_boolean() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = PrefStoreRepository(appContext)

        CoroutineScope(Dispatchers.Default).launch {
            repo.getBoolean(PrefStoreRepository.NOTIFICATION_ENABLED_KEY).collect {
                Assert.assertEquals(true, it)
            }
        }
        runBlocking { repo.setBoolean(PrefStoreRepository.NOTIFICATION_ENABLED_KEY, true) }
    }

    @Test
    fun happy_path_insert_retrieve_reified_boolean() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = PrefStoreRepository(appContext)

        repo.set(PrefStoreRepository.NOTIFICATION_ENABLED_KEY, true)
        val isNotificationEnabled = repo.get(PrefStoreRepository.NOTIFICATION_ENABLED_KEY, false)
        Assert.assertEquals(true, isNotificationEnabled)
    }

    @Test
    fun happy_path_insert_retrieve_reified_int() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = PrefStoreRepository(appContext)

        repo.set(PrefStoreRepository.LAST_FRAGMENT_KEY, 22)
        val id = repo.get(PrefStoreRepository.LAST_FRAGMENT_KEY, 1)
        Assert.assertEquals(22, id)
    }

    @Test
    fun happy_path_insert_retrieve_reified_string() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = PrefStoreRepository(appContext)

        repo.set(PrefStoreRepository.STRING_FLAG, "abc")
        val str = repo.get(PrefStoreRepository.STRING_FLAG, "a")
        Assert.assertEquals("abc", str)
    }

    @Test
    fun happy_path_insert_retrieve_reified_long() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repo = PrefStoreRepository(appContext)

        repo.set(PrefStoreRepository.LONG_FLAG, 10L)
        val number = repo.get(PrefStoreRepository.LONG_FLAG, 1L)
        Assert.assertEquals(10L, number)
    }
}
