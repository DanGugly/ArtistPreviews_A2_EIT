package com.example.artistgenresapp

import android.app.Application
import com.example.artistgenresapp.di.AppModule
import com.example.artistgenresapp.di.ArtistGenresAppComponent
import com.example.artistgenresapp.di.DaggerArtistGenresAppComponent

class SongsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        musicAppComponent = DaggerArtistGenresAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
    companion object{
        lateinit var musicAppComponent: ArtistGenresAppComponent
    }
}