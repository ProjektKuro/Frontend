package com.wimatt.ux.drinkanddare.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Shop(
        @PrimaryKey val shopId: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "state")
        var location: String,

        @ColumnInfo(name = "address")
        var address: String
)