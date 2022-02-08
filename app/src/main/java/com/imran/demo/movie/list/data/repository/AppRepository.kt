package com.imran.demo.movie.list.data.repository

import androidx.lifecycle.LiveData
import com.imran.demo.movie.list.AppScheduler
import com.imran.demo.movie.list.data.model.AppModel

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */
class AppRepository {

    suspend fun saveAppData(model: AppModel) {
        AppScheduler.appDb.appItemDao().save(model)
    }

    suspend fun getAllSaveAppList(): List<AppModel> {
        return AppScheduler.appDb.appItemDao().getAllAppsItem()
    }

    suspend fun delete(model: AppModel) {
        AppScheduler.appDb.appItemDao().deleteAppWithPackage(model.appPackageName)
    }

}
