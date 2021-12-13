package com.example.artistgenresapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.artistgenresapp.adapter.ClassicAdapter
import com.example.artistgenresapp.adapter.FragmentAdapter
import com.example.artistgenresapp.adapter.PreviewClick
import com.example.artistgenresapp.databinding.ActivityMainBinding
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.*
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IRockView, IPopView, IClassicView, PreviewClick {
    @Inject
    lateinit var presenterC: IClassicPresenter
    @Inject
    lateinit var presenterP: IPopPresenter
    @Inject
    lateinit var presenterR: IRockPresenter

    private var classicAdapter = ClassicAdapter(this)

    private lateinit var binding: ActivityMainBinding
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SongsApplication.musicAppComponent.inject(this)

        presenterR.initPresenter(this)
        presenterP.initPresenter(this)
        presenterC.initPresenter(this)

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

    override fun onResume() {
        super.onResume()
        swipeRefreshLayout = binding.swipe
        swipeRefreshLayout.setOnRefreshListener {
            // Do API fetch for all genres
            Toast.makeText(this,"Refreshing..",Toast.LENGTH_LONG).show()
            presenterC.getClassicSongsFromServer()
            presenterP.getPopSongsFromServer()
            presenterR.getRockSongsFromServer()
            Handler().postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }

    }

    override fun onErrorNetwork() {
        presenterC.getLocalData()
        presenterP.getLocalData()
        presenterR.getLocalData()
    }

    override fun classicSongsUpdated(classicSongs: List<Result>) {
        Log.d("MAIN", "classic refresh ${classicSongs[0].artistName}")
        classicAdapter.updateClassic(classicSongs)
    }

    override fun popSongsUpdated(popSongs: List<Result>) {
        Log.d("MAIN", "pop refresh ${popSongs[0].artistName}")
    }

    override fun rockSongsUpdated(rockSongs: List<Result>) {
        Log.d("MAIN", "rock refresh ${rockSongs[0].artistName}")
    }

    override fun onErrorData(error: Throwable) {
        Toast.makeText(applicationContext, error.localizedMessage, Toast.LENGTH_LONG).show()
        Log.e("ClassicFragment", error.stackTraceToString())
    }

    override fun previewSong(previewUrl: String?, songName: String?) {

    }
}