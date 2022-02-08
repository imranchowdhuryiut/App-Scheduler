package com.imran.demo.movie.list.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imran.demo.movie.list.data.db.dao.AppItemDao
import com.imran.demo.movie.list.data.model.AppModel

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */

@Database(
    entities = [
        AppModel::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDb: RoomDatabase() {
    abstract fun appItemDao(): AppItemDao
}

