package com.example.artistgenresapp.presenter

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.artistgenresapp.database.ResultDatabase
import com.example.artistgenresapp.model.Result
import com.example.artistgenresapp.rest.SongApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopPresenter @Inject constructor(
    var networkApi: SongApi,
    var connectivityManager: ConnectivityManager,
    var resultDatabase: ResultDatabase
) : IPopPresenter {

    private var popViewContract: IPopView? = null
    private var isNetworkAvailable = false
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun initPresenter(viewContract: IPopView) {
        popViewContract = viewContract
    }

    override fun getPopSongsFromServer() {
        if(isNetworkAvailable){
            val musicDisposable = networkApi
                .getSongs(POP_GENRE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {   popSongs ->
                        popViewContract?.popSongsUpdated(popSongs.results)
                    },
                    {   throwable ->
                        popViewContract?.onErrorData(throwable)
                        popViewContract?.onErrorNetwork()
                    }
                )
            disposable.add(musicDisposable)
        } else{
            popViewContract?.onErrorNetwork()
        }
    }

    override fun saveResultsToDB(result: List<Result>) {
        val databaseDisposable = resultDatabase
            .getResultDao()
            .insertAll(result)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d("PRESENTER", "Pop list saved") },
                { Log.e("PRESENTER", it.localizedMessage)}
            )
        disposable.add(databaseDisposable)
    }

    override fun getLocalData(){
        val databaseDisposable = resultDatabase
            .getResultDao()
            .getPopMusic()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    popViewContract?.popSongsUpdated(it)
                    Log.d("PRESENTER", "Pop list retrieved")
                },
                { Log.e("PRESENTER", it.localizedMessage)}
            )
        disposable.add(databaseDisposable)
    }

    override fun checkNetworkState() {
        isNetworkAvailable = getActiveNetworkCapabilities()?.let {
            it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false
    }

    override fun destroyPresenter() {
        disposable.clear()
        popViewContract = null
    }

    private fun getActiveNetworkCapabilities(): NetworkCapabilities? {
        return connectivityManager.activeNetwork.let {
            connectivityManager.getNetworkCapabilities(it)
        }
    }
    companion object{
        const val POP_GENRE = "pop"
    }
}

interface IPopPresenter{
    fun initPresenter(viewContract: IPopView)

    fun getPopSongsFromServer()

    fun saveResultsToDB(result: List<Result>)

    fun getLocalData()

    fun checkNetworkState()

    fun destroyPresenter()
}

interface IPopView{

    fun onErrorNetwork()

    fun popSongsUpdated(popSongs: List<Result>)

    fun onErrorData(error: Throwable)
}