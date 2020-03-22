package com.wimatt.ux.drinkanddare.room.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class Product(
        @PrimaryKey val productId: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "state")
        var state: Int
)
