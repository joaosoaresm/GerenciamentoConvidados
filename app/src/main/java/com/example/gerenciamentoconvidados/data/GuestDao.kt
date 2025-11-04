package com.exemplo.gerenciamentoconvidados.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GuestDao {
    @Query("SELECT * FROM guest")
    fun getAllGuests(): Flow<List<Guest>>

    @Query("SELECT * FROM guest WHERE id = :id")
    fun getGuestById(id: Int): Flow<Guest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guest: Guest)

    @Update
    suspend fun updateGuest(guest: Guest)

    @Delete
    suspend fun deleteGuest(guest: Guest)

    @Query("SELECT COUNT(*) FROM guest WHERE rsvp = 'SIM'")
    fun getConfirmedCount(): Flow<Int>
}