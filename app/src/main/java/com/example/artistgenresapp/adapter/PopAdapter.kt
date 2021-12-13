package com.example.artistgenresapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artistgenresapp.R
import com.example.artistgenresapp.model.Result
import com.squareup.picasso.Picasso

class PopAdapter(
    private val previewClick: PreviewClick,
    private val classicList: MutableList<Result> = mutableListOf()
) : RecyclerView.Adapter<PopAdapterViewHolder>(){

    fun updatePop(newPop: List<Result>){
        classicList.clear()
        classicList.addAll(newPop)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopAdapterViewHolder {
        val popView = LayoutInflater.from(parent.context).inflate(
            R.layout.song_items,
            parent,
            false
        )
        return PopAdapterViewHolder(popView)
    }

    override fun onBindViewHolder(holder: PopAdapterViewHolder, position: Int) {
        val song = classicList[position]
        holder.artistName.text = song.artistName
        holder.trackPrice.text = song.trackPrice.toString()+" "+ song.currency
        holder.collectionName.text = song.collectionName
        getWeatherIcon(song.artworkUrl60, holder.artwork)
        holder.itemView.setOnClickListener{
            previewClick.previewSong(song.previewUrl,song.trackName)
        }
    }

    private fun getWeatherIcon(path:String?, artwork: ImageView){
        Picasso
            .get()
            .load(path)
            .resize(250,250)
            //.centerCrop()
            .into(artwork)
    }

    override fun getItemCount(): Int = classicList.size

}

class PopAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var artistName : TextView = itemView.findViewById(R.id.artistName)
    var collectionName : TextView = itemView.findViewById(R.id.collectionName)
    var artwork : ImageView = itemView.findViewById(R.id.artwork)
    var trackPrice : TextView = itemView.findViewById(R.id.trackPrice)
}