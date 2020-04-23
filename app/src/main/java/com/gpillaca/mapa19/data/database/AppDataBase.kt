package com.gpillaca.mapa19.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val DATA_BASE = "mapa19.db"

@Database(entities = [VulnerablePerson::class], exportSchema = false, version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun vulnerablePersonDao(): VulnerablePersonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun build(context: Context): AppDataBase {

            return INSTANCE ?: synchronized(AppDataBase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    DATA_BASE
                ).fallbackToDestructiveMigration().addCallback(AppDataBaseCallback()).build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDataBaseCallback() : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let { appDataBase ->
                        appDataBase.vulnerablePersonDao().deleteAll()
                    }
                }
            }
        }
    }
}