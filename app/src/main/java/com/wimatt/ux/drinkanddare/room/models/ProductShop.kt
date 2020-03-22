package com.wimatt.ux.drinkanddare.room.models

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "songId"])
data class ProductShop(
        val shopId: Long,
        val productId: Long
)