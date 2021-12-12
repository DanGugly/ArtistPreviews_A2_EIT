package com.example.artistgenresapp.di

import android.net.ConnectivityManager
import com.example.artistgenresapp.presenter.*
import com.example.artistgenresapp.rest.SongApi
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {
    @Provides
    fun provideRockPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager
    ) : IRockPresenter{
            return RockPresenter(songApi,connectivityManager)
    }
    @Provides
    fun provideClassicPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager
    ) : IClassicPresenter {
        return ClassicPresenter(songApi,connectivityManager)
    }
    @Provides
    fun providePopPresenter(
        songApi: SongApi,
        connectivityManager: ConnectivityManager
    ) : IPopPresenter {
        return PopPresenter(songApi,connectivityManager)
    }
}