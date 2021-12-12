package com.example.artistgenresapp.di

import android.net.ConnectivityManager
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
}