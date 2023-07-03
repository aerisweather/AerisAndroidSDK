package com.xweather.sdkdemo.kotlin.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/*
 * TODO
 * 1. Test of CRUD operator
 * 2. Test of Migration
 */
@RunWith(AndroidJUnit4::class)
class RoomReadWriteTest {
    private lateinit var myPlaceDao: MyPlaceDao
    private lateinit var db: MyPlaceDatabase
    private lateinit var repo: MyPlaceRepository
    val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun createDb() {
        db = Room.databaseBuilder(context, MyPlaceDatabase::class.java, "my_place_database").build()
        myPlaceDao = db.myPlaceDao()
        repo = MyPlaceRepository(myPlaceDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun happy_path_insert_retrieve_all() {
        createDb()
        val minne = MyPlace(
            "minneapolis",
            "MN",
            "USA",
            true,
            44.97,
            -93.26,
        )

        runBlocking { myPlaceDao.deleteAll() }

        CoroutineScope(Dispatchers.Default).launch {
            repo.allPlaces.collect {
                val sze = it.size
                Assert.assertEquals(1, sze)
                val loca = it.get(0)
                Assert.assertEquals("minneapolis", loca.name)
                Assert.assertEquals("MN", loca.state)
                Assert.assertEquals("USA", loca.country)
                Assert.assertEquals(true, loca.myLoc)
                Assert.assertEquals(44.97, loca.latitude)
                Assert.assertEquals(-93.26, loca.longitude)
            }
        }
        runBlocking { myPlaceDao.insert(minne) }
        closeDb()
    }

    @Test
    @Throws(Exception::class)
    fun happy_path_insert_delete_all() {
        createDb()
        val minne = MyPlace(
            "minneapolis",
            "MN",
            "USA",
            true,
            44.97,
            -93.26,
        )
        runBlocking { myPlaceDao.insert(minne) }

        CoroutineScope(Dispatchers.Default).launch {
            repo.allPlaces.collect {
                val sze = it.size
                Assert.assertEquals(0, sze)
            }
            myPlaceDao.deleteAll()
        }
        closeDb()
    }
}
