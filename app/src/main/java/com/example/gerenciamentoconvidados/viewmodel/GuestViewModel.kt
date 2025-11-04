package com.exemplo.gerenciamentoconvidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.exemplo.gerenciamentoconvidados.data.AppDatabase
import com.exemplo.gerenciamentoconvidados.data.Guest
import com.exemplo.gerenciamentoconvidados.data.GuestDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GuestViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: GuestDao = AppDatabase.getDatabase(application).guestDao()

    val allGuests: Flow<List<Guest>> = dao.getAllGuests()
    val confirmedCount: Flow<Int> = dao.getConfirmedCount()

    fun insertGuest(guest: Guest) {
        viewModelScope.launch {
            dao.insertGuest(guest)
        }
    }

    fun updateGuest(guest: Guest) {
        viewModelScope.launch {
            dao.updateGuest(guest)
        }
    }

    fun deleteGuest(guest: Guest) {
        viewModelScope.launch {
            dao.deleteGuest(guest)
        }
    }
}