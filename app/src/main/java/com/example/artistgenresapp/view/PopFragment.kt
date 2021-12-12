package com.example.artistgenresapp.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artistgenresapp.R
import com.example.artistgenresapp.SongsApplication
import com.example.artistgenresapp.adapter.PopAdapter
import com.example.artistgenresapp.adapter.PreviewClick
import com.example.artistgenresapp.databinding.FragmentPopBinding
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.IPopPresenter
import com.example.artistgenresapp.presenter.IPopView
import javax.inject.Inject

class PopFragment : Fragment(), IPopView, PreviewClick {

    @Inject
    lateinit var presenter: IPopPresenter
    private lateinit var binding: FragmentPopBinding
    private var popAdapter = PopAdapter(this)

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
        if(!dataLoaded){
            presenter.getPopSongsFromServer()
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
        binding = FragmentPopBinding.inflate(inflater, container, false)
        binding.popRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = popAdapter
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroyPresenter()
    }

    override fun previewSong(previewUrl: String, songName: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse(previewUrl), "audio/*")
        Toast.makeText(requireContext(), "Now Playing: $songName", Toast.LENGTH_LONG).show()
        startActivity(intent)
    }

    override fun popSongsUpdated(popSongs: List<Result>) {
        popAdapter.updatePop(popSongs)
        dataLoaded = true
        Toast.makeText(requireContext(), "Results: "+(popSongs.size).toString(), Toast.LENGTH_LONG).show()
        Log.d("PopFragment", popSongs.toString())
    }

    override fun onErrorData(error: Throwable) {
        Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
        Log.e("PopFragment", error.stackTraceToString())
    }

    companion object {
        private var dataLoaded : Boolean = false
        @JvmStatic
        fun newInstance() = PopFragment()
    }
}