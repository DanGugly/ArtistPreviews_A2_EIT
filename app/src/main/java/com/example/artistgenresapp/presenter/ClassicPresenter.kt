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

class ClassicPresenter @Inject constructor(
    var networkApi: SongApi,
    var connectivityManager: ConnectivityManager,
    var resultDatabase: ResultDatabase
) : IClassicPresenter {

    private var classicViewContract: IClassicView? = null
    private var isNetworkAvailable = false
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun initPresenter(viewContract: IClassicView) {
        classicViewContract = viewContract
    }

    override fun getClassicSongsFromServer() {
        if(isNetworkAvailable){
            val musicDisposable = networkApi
                .getSongs(CLASSIC_GENRE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {   classicSongs ->
                        classicViewContract?.classicSongsUpdated(classicSongs.results)
                    },
                    {   throwable ->
                        classicViewContract?.onErrorNetwork()
                        classicViewContract?.onErrorData(throwable)
                    }
                )
            disposable.add(musicDisposable)
        } else{
            classicViewContract?.onErrorNetwork()
        }
    }

    override fun saveResultsToDB(result: List<Result>) {
        val databaseDisposable = resultDatabase
            .getResultDao()
            .insertAll(result)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d("PRESENTER", "Classic list saved") },
                { Log.e("PRESENTER", it.localizedMessage)}
            )
        disposable.add(databaseDisposable)
    }

    override fun getLocalData() {
        val databaseDisposable = resultDatabase
            .getResultDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    classicViewContract?.classicSongsUpdated(it)
                    Log.d("PRESENTER", "Classic list retrieved")
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
        classicViewContract = null
    }

    private fun getActiveNetworkCapabilities(): NetworkCapabilities? {
        return connectivityManager.activeNetwork.let {
            connectivityManager.getNetworkCapabilities(it)
        }
    }
    companion object{
        const val CLASSIC_GENRE = "classick"
    }
}

interface IClassicPresenter{
    fun initPresenter(viewContract: IClassicView)

    fun getClassicSongsFromServer()

    fun saveResultsToDB(result: List<Result>)

    fun getLocalData()

    fun checkNetworkState()

    fun destroyPresenter()
}

interface IClassicView{

    fun onErrorNetwork()

    fun classicSongsUpdated(classicSongs: List<Result>)

    fun onErrorData(error: Throwable)
}