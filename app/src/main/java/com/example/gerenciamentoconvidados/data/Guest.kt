package com.exemplo.gerenciamentoconvidados.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest")
data class Guest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val group_name: String? = null,
    val rsvp: String = "TALVEZ", // Valores: "SIM", "NAO", "TALVEZ"
    val drinks: String? = null,
    val notes: String? = null
)