package com.imran.demo.movie.list.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Created by Md. Imran Chowdhury on 5/9/2021.
 */

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(data: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(vararg data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(data: T): Long

    @Delete
    abstract suspend fun delete(vararg data: T)

    @Delete
    abstract suspend fun delete(data: List<T>)

}
