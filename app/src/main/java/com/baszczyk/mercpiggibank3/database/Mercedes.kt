package com.baszczyk.mercpiggibank3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mercedes")
data class Mercedes(@PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "mercedes_id") var mercedesId: Long = 0L,
                    @ColumnInfo(name = "name")val name: String = "Mercedes",
                    @ColumnInfo(name = "surname")val surname: String,
                    @ColumnInfo(name = "price")val price: Double,
                    @ColumnInfo(name = "version")val version: String,
                    @ColumnInfo(name = "engine_capacity")val engineCapacity: String,
                    @ColumnInfo(name = "engine_power")val enginePower: String)

@Entity(tableName = "piggy")
data class PiggyBank(@PrimaryKey(autoGenerate = true)
                     @ColumnInfo(name = "piggy_id")var piggyId: Long = 0L,
                    @ColumnInfo(name = "mercedes_id") val mercedesId: Long,
                    @ColumnInfo(name = "actual_amount") var actualAmount: Double,
                    @ColumnInfo(name = "user_id") var userId: Long) {

}



@Entity(tableName = "deposit")
data class Deposit(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "deposit_id")var depositId: Long = 0L,
                   @ColumnInfo(name = "amount")val amount: Double,
                   @ColumnInfo(name = "data")val data: String,
                   @ColumnInfo(name = "mercedes_id")val mercedesId: Long) {

}

@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "user_id") var userId: Long = 0L,
                @ColumnInfo(name = "user")val name: String,
                @ColumnInfo(name = "password")val password: String,
                @ColumnInfo(name = "email")val email: String) {

}

fun addToPiggyBank(actualAmount: Double, amount: Double) {
    val x = actualAmount - amount
}