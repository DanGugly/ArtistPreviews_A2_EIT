package com.example.artistgenresapp.di

import com.example.artistgenresapp.MainActivity
import com.example.artistgenresapp.view.ClassicFragment
import com.example.artistgenresapp.view.PopFragment
import com.example.artistgenresapp.view.RockFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PresenterModule::class
])
@Singleton
interface ArtistGenresAppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(rockFragment: RockFragment)
    fun inject(classicFragment: ClassicFragment)
    fun inject(popFragment: PopFragment)
}