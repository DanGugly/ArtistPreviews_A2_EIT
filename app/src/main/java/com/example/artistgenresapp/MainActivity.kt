package com.example.artistgenresapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.artistgenresapp.adapter.FragmentAdapter
import com.example.artistgenresapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.artistGenreContainer.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.artistGenreMenu, binding.artistGenreContainer){ tab, position ->
            when(position){
                0 -> {
                    tab.text = "Rock"
                    tab.icon = getDrawable(R.drawable.ic_rock_music)
                }
                1 -> {
                    tab.text = "Classic"
                    tab.icon = getDrawable(R.drawable.ic_classic_music)
                }
                else -> {
                    tab.text = "Pop"
                    tab.icon = getDrawable(R.drawable.ic_pop_music)
                }
            }
        }.attach()
    }
}