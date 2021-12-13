package com.example.artistgenresapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.artistgenresapp.model.Result

@Database(entities = [Result::class], version = 1)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun getResultDao(): ResultDao
}