package com.example.hht_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.time.LocalDateTime

@Entity(tableName = "bsm")
data class BSM(
    @PrimaryKey
    val bagTagNumber: String,
    val flightNumber: String,
    val destination: String,
    val passengerName: String,
    val isProcessed: Boolean = false
)