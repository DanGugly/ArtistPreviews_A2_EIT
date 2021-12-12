package com.example.artistgenresapp.di

import android.net.ConnectivityManager
import com.example.artistgenresapp.presenter.ClassicPresenter
import com.example.artistgenresapp.presenter.IClassicPresenter
import com.example.artistgenresapp.presenter.IRockPresenter
import com.example.artistgenresapp.presenter.RockPresenter
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
}