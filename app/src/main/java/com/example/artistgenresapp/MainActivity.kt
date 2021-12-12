package com.example.artistgenresapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.content.res.AppCompatResources
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.artistgenresapp.adapter.FragmentAdapter
import com.example.artistgenresapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swipeRefreshLayout = binding.swipe
        swipeRefreshLayout.setOnRefreshListener {
            // Do API fetch for all genres
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }

        binding.artistGenreContainer.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.artistGenreMenu, binding.artistGenreContainer){ tab, position ->
            when(position){
                0 -> {
                    tab.text = "Rock"
                    tab.icon = AppCompatResources.getDrawable(applicationContext,R.drawable.ic_rock_music)
                }
                1 -> {
                    tab.text = "Classic"
                    tab.icon = AppCompatResources.getDrawable(applicationContext,R.drawable.ic_classic_music)
                }
                else -> {
                    tab.text = "Pop"
                    tab.icon = AppCompatResources.getDrawable(applicationContext,R.drawable.ic_pop_music)
                }
            }
        }.attach()
    }
}