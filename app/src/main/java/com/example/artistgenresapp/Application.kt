package com.example.artistgenresapp

import android.app.Application
import com.example.artistgenresapp.di.ArtistGenresAppComponent
import com.example.artistgenresapp.di.DaggerArtistGenresAppComponent

class Application : Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: ArtistGenresAppComponent by lazy {
        DaggerArtistGenresAppComponent.factory().create(applicationContext)
    }

}