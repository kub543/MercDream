package com.baszczyk.mercpiggibank3.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "piggy")
data class PiggyBank(@PrimaryKey(autoGenerate = true)
                     @ColumnInfo(name = "piggy_id")var piggyId: Long = 0L,
                     @ColumnInfo(name = "piggy_name")val piggyName: String,
                     @ColumnInfo(name = "mercedes_id") val mercedesId: Long,
                     @ColumnInfo(name = "actual_amount") var actualAmount: Double,
                     @ColumnInfo(name = "user_id") var userId: Long)