package com.baszczyk.mercpiggibank3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface PiggyDatabaseDao {
    @Insert
    abstract fun insertMercedes(mercedes: Mercedes)
    @Insert
    abstract fun insertPiggyBank(piggyBank: PiggyBank)
    @Insert
    abstract fun insertDeposit(deposit: Deposit)
    @Insert(onConflict = REPLACE)
    abstract fun insertNewUser(user: User)

    @Query("SELECT mercedes_id FROM mercedes ORDER BY mercedes_id DESC LIMIT 1")
    fun getMercedesId(): Long?

    @Query("SELECT * FROM piggy WHERE user_id = :userId")
    fun getAllPiggies(userId: Long): List<PiggyBank>

    @Query("SELECT * FROM user ORDER BY user_id DESC LIMIT 1")
    suspend fun getCurrentUser() : User

    @Query("SELECT password FROM user WHERE user = :name")
    fun getUserPassword(name: String): String

    @Query("SELECT * FROM user WHERE user = :name")
    fun getUser(name: String): User?

    @Query("SELECT piggy_id FROM piggy WHERE user_id = :userId")
    fun getPiggyId(userId: Long): List<Long?>

    @Query("SELECT user FROM user")
    fun getAllUsersName(): List<String>
}
