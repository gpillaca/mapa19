package com.gpillaca.mapa19.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATA_BASE = "mapa19.db"

@Database(entities = [VulnerablePerson::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun vulnerablePersonDao(): VulnerablePersonDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            DATA_BASE
        ).build()
    }
}