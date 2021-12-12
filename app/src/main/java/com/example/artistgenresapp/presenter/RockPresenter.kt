package com.example.artistgenresapp.presenter

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.rest.SongApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RockPresenter @Inject constructor(
    var networkApi: SongApi,
    var connectivityManager: ConnectivityManager
) : IRockPresenter {

    private var rockViewContract: IRockView? = null
    private var isNetworkAvailable = false
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun initPresenter(viewContract: IRockView) {
        rockViewContract = viewContract
    }

    override fun getRockSongsFromServer() {
        if(isNetworkAvailable){
            val musicDisposable = networkApi
                .getSongs(ROCK_GENRE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {   rockSongs ->
                        rockViewContract?.rockSongsUpdated(rockSongs.results)
                    },
                    {   throwable ->
                        rockViewContract?.onErrorData(throwable)
                    }
                )
            disposable.add(musicDisposable)
        }
    }

    override fun checkNetworkState() {
        isNetworkAvailable = getActiveNetworkCapabilities()?.let {
            it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false
    }

    override fun destroyPresenter() {
        disposable.clear()
        rockViewContract = null
    }

    private fun getActiveNetworkCapabilities(): NetworkCapabilities? {
        return connectivityManager.activeNetwork.let {
            connectivityManager.getNetworkCapabilities(it)
        }
    }
    companion object{
        const val ROCK_GENRE = "rock"
    }
}

interface IRockPresenter{
    fun initPresenter(viewContract: IRockView)

    fun getRockSongsFromServer()

    fun checkNetworkState()

    fun destroyPresenter()
}

interface IRockView{
    fun rockSongsUpdated(rock_songs: List<Result>)

    fun onErrorData(error: Throwable)
}