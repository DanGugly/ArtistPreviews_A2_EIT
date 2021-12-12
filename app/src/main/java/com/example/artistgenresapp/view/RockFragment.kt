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
import com.example.artistgenresapp.adapter.RockAdapter
import com.example.artistgenresapp.databinding.FragmentRockBinding
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.IRockPresenter
import com.example.artistgenresapp.presenter.IRockView
import javax.inject.Inject

class RockFragment : Fragment(), IRockView {

    @Inject
    lateinit var presenter: IRockPresenter

    private lateinit var binding: FragmentRockBinding
    private var rockAdapter = RockAdapter()

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

    override fun onResume() {
        super.onResume()
        presenter.getRockSongsFromServer()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RockFragment()
    }

    override fun rockSongsUpdated(rock_songs: List<Result>) {
        rockAdapter.updateRock(rock_songs)
        Toast.makeText(requireContext(), rock_songs[0].artistName, Toast.LENGTH_LONG).show()
        Log.d("CharactersFragment", rock_songs.toString())
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
}