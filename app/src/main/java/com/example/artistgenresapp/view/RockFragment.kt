package com.example.artistgenresapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistgenresapp.SongsApplication
import com.example.artistgenresapp.adapter.PreviewClick
import com.example.artistgenresapp.adapter.RockAdapter
import com.example.artistgenresapp.databinding.FragmentRockBinding
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.IRockPresenter
import com.example.artistgenresapp.presenter.IRockView
import javax.inject.Inject
import android.content.Intent
import android.net.Uri


class RockFragment : Fragment(), IRockView, PreviewClick {

    @Inject
    lateinit var presenter: IRockPresenter

    private lateinit var binding: FragmentRockBinding
    private var rockAdapter = RockAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        SongsApplication.musicAppComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initPresenter(this)
        presenter.checkNetworkState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRockBinding.inflate(inflater, container, false)
        binding.rockRecycler.apply{
            // adding the layout manager
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            // setting the adapter
            adapter = rockAdapter
        }
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        dataLoaded = false
    }

    override fun onResume() {
        super.onResume()
        if (!dataLoaded){
            presenter.getRockSongsFromServer()
        }
    }

    override fun rockSongsUpdated(rockSongs: List<Result>) {
        rockAdapter.updateRock(rockSongs)
        dataLoaded = true
        Toast.makeText(requireContext(), "Results: "+(rockSongs.size+1).toString(), Toast.LENGTH_LONG).show()
        Log.d("CharactersFragment", rockSongs.toString())
    }

    override fun onErrorData(error: Throwable) {
        Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
        Log.e("CharactersFragment", error.stackTraceToString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // this method will clear the disposable from the presenter
        presenter.destroyPresenter()
    }

    override fun previewSong(previewUrl: String, songName : String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(previewUrl), "audio/*")
        Toast.makeText(requireContext(), "Now Playing: $songName", Toast.LENGTH_LONG).show()
        startActivity(intent)
        /*
            try {
                var mediaPlayer = MediaPlayer()
                mediaPlayer.apply {
                    setDataSource(previewUrl)
                    setAudioAttributes(AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                    )
                    prepare()
                }
                mediaPlayer.start()
                Toast.makeText(requireContext(), "Now Playing: $songName", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                mediaPlayer.release()
            } */
    }

    companion object {
        private var dataLoaded : Boolean = false
        @JvmStatic
        fun newInstance() = RockFragment()
    }
}