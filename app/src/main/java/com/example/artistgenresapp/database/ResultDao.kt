package com.example.artistgenresapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import com.example.artistgenresapp.model.Result
import io.reactivex.Completable

@Dao
interface ResultDao {
    @Query("select * from result")
    fun getAll(): Single<List<Result>>

    @Query("select * from result where primaryGenreName like '%Rock%'")
    fun getRockMusic(): Single<List<Result>>

    @Query("select * from result where primaryGenreName like '%Classic%'")
    fun getClassicMusic(): Single<List<Result>>

    @Query("select * from result where primaryGenreName like '%Pop%'")
    fun getPopMusic(): Single<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(results: List<Result>): Completable
}