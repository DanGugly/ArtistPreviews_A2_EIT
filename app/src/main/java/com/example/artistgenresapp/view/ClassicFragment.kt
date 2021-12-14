package com.example.artistgenresapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistgenresapp.SongsApplication
import com.example.artistgenresapp.adapter.ClassicAdapter
import com.example.artistgenresapp.adapter.PreviewClick
import com.example.artistgenresapp.databinding.FragmentSongsBinding
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.IClassicPresenter
import com.example.artistgenresapp.presenter.IClassicView
import javax.inject.Inject

class ClassicFragment : Fragment(), IClassicView, PreviewClick {

    @Inject
    lateinit var presenter: IClassicPresenter

    private lateinit var binding: FragmentSongsBinding
    private var classicAdapter = ClassicAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SongsApplication.musicAppComponent.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        dataLoaded = false
    }

    override fun onResume() {
        super.onResume()
        if (!dataLoaded) {
            presenter.getClassicSongsFromServer()
        }
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
        binding = FragmentSongsBinding.inflate(inflater, container, false)
        binding.songRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = classicAdapter
        }
        binding.swipe.setOnRefreshListener {
            Toast.makeText(requireContext(),"Refreshing..",Toast.LENGTH_SHORT).show()
            presenter.getClassicSongsFromServer()
            binding.swipe.isRefreshing = false
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroyPresenter()
    }

    override fun previewSong(previewUrl: String?, songName: String?) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(previewUrl), "audio/*")
        Toast.makeText(requireContext(), "Now Playing: $songName", Toast.LENGTH_LONG).show()
        startActivity(intent)
    }

    override fun classicSongsUpdated(classicSongs: List<Result>) {
        classicAdapter.updateClassic(classicSongs)
        presenter.saveResultsToDB(classicSongs)
        dataLoaded = true
        Toast.makeText(requireContext(), "Results: "+(classicSongs.size).toString(), Toast.LENGTH_LONG).show()
        Log.d("ClassicFragment", classicSongs.toString())
    }

    override fun onErrorNetwork(){
        presenter.getLocalData()
    }

    override fun onErrorData(error: Throwable) {
        Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
        Log.e("ClassicFragment", error.stackTraceToString())
    }
    companion object {
        private var dataLoaded : Boolean = false
        @JvmStatic
        fun newInstance() = ClassicFragment()
    }

}