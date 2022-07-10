package com.aqsl.teamupfp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.aqsl.teamupfp.Db.DbClient.Companion.getInstance
import androidx.lifecycle.LiveData
import com.aqsl.teamupfp.Db.DAO.InterfaceDAO
import com.aqsl.teamupfp.Model.ModelDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    var dataLaporan: LiveData<List<ModelDb>>
    var databaseDao: InterfaceDAO? = getInstance(application)?.appDatabase?.databaseDao()

    fun deleteDatabyId(uid: Int){
        Completable.fromAction {
            databaseDao?.deleteHistoryById(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


    init{
        dataLaporan = databaseDao!!.getAllHistory()
    }
}