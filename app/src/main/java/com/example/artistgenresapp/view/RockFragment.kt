package com.example.artistgenresapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.artistgenresapp.R
import com.example.artistgenresapp.SongsApplication
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.presenter.IRockPresenter
import com.example.artistgenresapp.presenter.IRockView
import javax.inject.Inject

class RockFragment : Fragment(), IRockView {

    @Inject
    lateinit var presenter: IRockPresenter

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock, container, false)
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