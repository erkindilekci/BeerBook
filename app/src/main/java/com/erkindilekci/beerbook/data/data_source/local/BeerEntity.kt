package com.erkindilekci.beerbook.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeerEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val firstBrewed: String,
    val imageUrl: String?
)