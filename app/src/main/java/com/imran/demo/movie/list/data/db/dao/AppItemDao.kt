package com.imran.demo.movie.list.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.imran.demo.movie.list.data.model.AppModel

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */

@Dao
abstract class AppItemDao: BaseDao<AppModel>() {
    @Query("SELECT * FROM app_item")
    abstract suspend fun getAllAppsItem(): List<AppModel>

    @Query("SELECT * FROM app_item")
    abstract fun getAllAppsItemLiveData(): LiveData<List<AppModel>>

    @Query("DELETE FROM app_item")
    abstract suspend fun deleteAll()

    @Query("DELETE FROM app_item WHERE appPackageName == :packageName")
    abstract suspend fun deleteAppWithPackage(packageName: String?)

}