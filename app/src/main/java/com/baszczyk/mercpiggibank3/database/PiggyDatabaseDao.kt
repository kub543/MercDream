package com.baszczyk.mercpiggibank3.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.baszczyk.mercpiggibank3.database.entities.Deposit
import com.baszczyk.mercpiggibank3.database.entities.Mercedes
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.entities.User

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

    @Query("SELECT * FROM mercedes WHERE mercedes_id = :id")
    fun getMercedes(id: Long): Mercedes

    @Query("SELECT * FROM piggy WHERE user_id = :userId ORDER BY piggy_id DESC")
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

    @Query("SELECT * FROM piggy WHERE piggy_id = :id")
    fun getPiggy(id: Long): PiggyBank

    @Query("UPDATE piggy SET actual_amount = :amount WHERE piggy_id = :id")
    fun updatePiggyBank(amount: Double, id: Long)

    @Query("SELECT * FROM deposit WHERE mercedes_id = :id ORDER BY deposit_id DESC")
    fun getAllDepositsForCurrentPiggy(id: Long): List<Deposit>

    @Query("SELECT * FROM deposit WHERE user_id = :id ORDER BY deposit_id DESC")
    fun getAllDepositsForCurrentUser(id: Long): List<Deposit>

    @Query("DELETE FROM piggy WHERE piggy_id = :id")
    fun deletePiggy(id: Long)
}
