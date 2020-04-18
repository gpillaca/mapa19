package com.gpillaca.mapa19.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VulnerablePersonDao {

    @Query("SELECT * FROM VulnerablePerson")
    fun getVAll(): List<VulnerablePerson>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>)
}