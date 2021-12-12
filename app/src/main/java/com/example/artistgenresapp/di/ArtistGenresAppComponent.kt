package com.example.artistgenresapp.di

import android.content.Context
import com.example.artistgenresapp.MainActivity
import com.example.artistgenresapp.view.ClassicFragment
import com.example.artistgenresapp.view.PopFragment
import com.example.artistgenresapp.view.RockFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [
    NetworkModule::class
])
@Singleton
interface ArtistGenresAppComponent {
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ArtistGenresAppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(rockFragment: RockFragment)
    fun inject(classicFragment: ClassicFragment)
    fun inject(popFragment: PopFragment)
}