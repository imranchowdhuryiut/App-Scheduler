package com.imran.demo.movie.list.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.imran.demo.movie.list.utils.Constants

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */

class PrefRepository constructor(val context: Context) {

    companion object {
        private const val SP_FETCH_TIME = "SP_FETCH_TIME"
        private const val SP_GENERES = "SP_GENERES"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    fun logTime(time: Long) {
        saveLongToSP(time, SP_FETCH_TIME)
    }

    fun getLogTime(): Long {
        return getLongFromSP(SP_FETCH_TIME)
    }

    fun getGenres(): Set<String>? {
        return getStringListFromSP(SP_GENERES)
    }

    fun setGenres(value: Set<String>?){
        saveStringSetToSP(value, SP_GENERES)
    }

    private fun saveStringSetToSP(value: Set<String>?, code: String) {
        val e = mPrefs.edit()
        e.putStringSet(code, value)
        e.apply()
    }

    private fun getLongFromSP(code: String): Long {
        return mPrefs.getLong(code, 0L)
    }

    private fun getStringListFromSP(code: String): Set<String>? {
        return mPrefs.getStringSet(code, setOf())
    }


    private fun saveLongToSP(value: Long, code: String) {
        val e = mPrefs.edit()
        e.putLong(code, value)
        e.apply()
    }

}