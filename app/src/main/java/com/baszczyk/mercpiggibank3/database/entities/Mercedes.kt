package com.baszczyk.mercpiggibank3.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mercedes")
data class Mercedes(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "mercedes_id") var mercedesId: Long = 0L,
                    @ColumnInfo(name = "name")val name: String = "Mercedes",
                    @ColumnInfo(name = "surname")val surname: String = "",
                    @ColumnInfo(name = "price")val price: Double,
                    @ColumnInfo(name = "version")val version: String = "",
                    @ColumnInfo(name = "engine_capacity")val engineCapacity: String = "",
                    @ColumnInfo(name = "engine_power")val enginePower: String = "")