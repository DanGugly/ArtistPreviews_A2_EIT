package com.example.artistgenresapp.rest

import com.example.artistgenresapp.model.Song
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SongApi {

    @GET(SEARCH)
    fun getSongs(
        @Query("term") genre : String,
        @Query("amp;media") media : String = SONG_MEDIA,
        @Query("amp;entity") entity : String = SONG_ENTITY,
        @Query("amp;limit") limit : String = SONG_LIMIT

    ) : Single<Song>

    companion object{
        private const val SONG_MEDIA = "music"
        private const val SONG_ENTITY = "song"
        private const val SONG_LIMIT = "50"
        const val SONG_CLASSICK = "classick"
        const val SONG_POP = "pop"
        const val SONG_ROCK = "rock"
        const val END = "&amp;media=music&amp;entity=song&amp;limit=50"
        const val SEARCH = "search"
        const val BASE_URL = "https://itunes.apple.com/"
    }
}