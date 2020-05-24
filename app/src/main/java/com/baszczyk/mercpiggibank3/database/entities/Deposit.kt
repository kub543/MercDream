package com.baszczyk.mercpiggibank3.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposit")
data class Deposit(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "deposit_id")var depositId: Long = 0L,
                   @ColumnInfo(name = "amount")val amount: Double,
                   @ColumnInfo(name = "data")val data: String,
                   @ColumnInfo(name = "mercedes_id")val mercedesId: Long,
                   @ColumnInfo(name = "user_id")val userId: Long,
                   @ColumnInfo(name = "piggy_name")val piggyName: String)
