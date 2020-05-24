package com.baszczyk.mercpiggibank3.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "user_id") var userId: Long = 0L,
                @ColumnInfo(name = "user")val name: String,
                @ColumnInfo(name = "password")val password: String,
                @ColumnInfo(name = "email")val email: String) {

}