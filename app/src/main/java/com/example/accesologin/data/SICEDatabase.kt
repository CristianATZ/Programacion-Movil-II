package com.example.accesologin.data

import androidx.room.RoomDatabase

abstract class SICEDatabase: RoomDatabase() {
    abstract fun UserLoginDao():  AccesoDao
}