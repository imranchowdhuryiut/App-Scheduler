package com.imran.demo.movie.list

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.imran.demo.movie.list.data.db.AppDb
import com.imran.demo.movie.list.data.repository.PrefRepository
import com.imran.demo.movie.list.utils.Constants

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */

class AppScheduler : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appPref: PrefRepository
        var tag = "AppScheduler"
        lateinit var appDb: AppDb
    }

    override fun onCreate() {
        super.onCreate()
        appPref = PrefRepository(this)
        appDb = Room.databaseBuilder(this, AppDb::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}