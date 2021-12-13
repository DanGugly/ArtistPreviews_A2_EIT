package com.example.artistgenresapp.di

import android.net.ConnectivityManager
import com.example.artistgenresapp.database.ResultDatabase
import com.example.artistgenresapp.presenter.*
import com.example.artistgenresapp.rest.SongApi
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideRockPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager,
        resultDatabase: ResultDatabase
    ) : IRockPresenter{
            return RockPresenter(songApi,connectivityManager,resultDatabase)
    }
    @Provides
    fun provideClassicPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager,
        resultDatabase: ResultDatabase
    ) : IClassicPresenter {
        return ClassicPresenter(songApi,connectivityManager,resultDatabase)
    }
    @Provides
    fun providePopPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager,
        resultDatabase: ResultDatabase
    ) : IPopPresenter {
        return PopPresenter(songApi,connectivityManager,resultDatabase)
    }
}