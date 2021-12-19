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

class ClassicAdapter(
    private val previewClick: PreviewClick,
    private val classicList: MutableList<Result> = mutableListOf()
) : RecyclerView.Adapter<ClassicAdapterViewHolder>(){

    fun updateClassic(newClassic: List<Result>){
        classicList.clear()
        classicList.addAll(newClassic)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassicAdapterViewHolder {
        val classicView = LayoutInflater.from(parent.context).inflate(
            R.layout.song_items,
            parent,
            false
        )
        return ClassicAdapterViewHolder(classicView)
    }

    override fun onBindViewHolder(holder: ClassicAdapterViewHolder, position: Int) {
        val song = classicList[position]
        holder.artistName.text = song.artistName
        holder.trackPrice.text = song.trackPrice.toString()+" "+ song.currency
        holder.collectionName.text = song.collectionName

        // here remember the SOLID principles (Single)

        // we can move this logic to the view holder directly
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

class ClassicAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var artistName : TextView = itemView.findViewById(R.id.artistName)
    var collectionName : TextView = itemView.findViewById(R.id.collectionName)
    var artwork : ImageView = itemView.findViewById(R.id.artwork)
    var trackPrice : TextView = itemView.findViewById(R.id.trackPrice)
}