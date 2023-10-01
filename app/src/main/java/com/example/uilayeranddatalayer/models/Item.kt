package com.example.uilayeranddatalayer.models

import java.sql.Date
import java.time.LocalDate

data class Item(
    var id: Int = 0,
    val content: String,
    val createdAt: LocalDate,
    val updatedAt: Date? = null
)
