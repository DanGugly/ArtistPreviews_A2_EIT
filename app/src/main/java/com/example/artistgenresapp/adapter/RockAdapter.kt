package com.example.artistgenresapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artistgenresapp.R
import com.example.artistgenresapp.model.Result

class RockAdapter(
    private val rockList: MutableList<Result> = mutableListOf()
) : RecyclerView.Adapter<RockAdapterViewHolder>(){

    fun updateRock(newRock: List<Result>){
        rockList.clear()
        rockList.addAll(newRock)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RockAdapterViewHolder {
        val rockView = LayoutInflater.from(parent.context).inflate(
            R.layout.song_items,
            parent,
            false
        )
        return RockAdapterViewHolder(rockView)
    }

    override fun onBindViewHolder(holder: RockAdapterViewHolder, position: Int) {
        val song = rockList[position]
        holder.songName.text = song.trackName
    }

    override fun getItemCount(): Int = rockList.size

}

class RockAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var songName : TextView = itemView.findViewById(R.id.song_name)
}