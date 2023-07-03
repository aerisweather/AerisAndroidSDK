package com.xweather.sdkdemo.kotlin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyPlace::class], version = 1, exportSchema = false)
abstract class MyPlaceDatabase : RoomDatabase() {
    abstract fun myPlaceDao(): MyPlaceDao

    /*
     * TODO consider this for testing and migration
     */
//    companion object {
//        @JvmField
//        val MIGRATION_1_2 = Migration1To2()
//        @Volatile
//        private var INSTANCE: MyPlaceDatabase? = null
//        fun getDatabase(
//            context: Context,
//            scope: CoroutineScope
//        ): MyPlaceDatabase {
//
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MyPlaceDatabase::class.java,
//                    "my_place_database"
//                )
//                      .addCallback(MyPlaceDatabaseCallback(scope))
//                    //       .addMigrations(MyPlaceDatabase.MIGRATION_1_2)
//                    .build()
//
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//    }
//    private class MyPlaceDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                scope.launch(Dispatchers.IO) {
//                    populateDatabase(database.myPlaceDao())
//                }
//            }
//        }
//
//        suspend fun populateDatabase(myPlaceDao: MyPlaceDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//            myPlaceDao.deleteAll()
//
//            var myPlace = MyPlace("name", "state", "country", true, 40.0, 100.0)
//            myPlaceDao.insert(myPlace)
//        }
//    }

    /*
     * References - migration
     * https://medium.com/@manuelvicnt/android-room-upgrading-alpha-versions-needs-a-migration-with-kotlin-or-nonnull-7a2d140f05b9
     */
//    class Migration1To2 : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            val TABLE_NAME_TEMP = "GameNew"
//
//            // 1. Create new table
//            database.execSQL(
//                "CREATE TABLE IF NOT EXISTS `$TABLE_NAME_TEMP` " +
//                        "(`name` TEXT," +
//                        " `state` TEXT," +
//                        " `country` TEXT," +
//                        " `myLoc` BOOLEAN," +
//                        " `latitude` DOUBLE," +
//                        " `longitude` DOUBLE," +
//                        " PRIMARY KEY(`name`, `state`, `country`))"
//            )
//
//            // 2. Remove the old table
//            database.execSQL("DROP TABLE my_place_table")
//
//            // 3. Change the table name to the correct one
//            database.execSQL("ALTER TABLE $TABLE_NAME_TEMP RENAME TO my_place_table")
//        }
//    }
}
