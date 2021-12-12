package com.example.artistgenresapp.adapter

import android.media.MediaPlayer

interface PreviewClick {
    fun previewSong(previewUrl: String, songName : String, mediaPlayer: MediaPlayer)
}