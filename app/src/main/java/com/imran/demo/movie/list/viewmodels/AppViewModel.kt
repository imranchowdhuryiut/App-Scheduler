package com.imran.demo.movie.list.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.demo.movie.list.data.model.AppModel
import com.imran.demo.movie.list.data.repository.AppRepository
import kotlinx.coroutines.launch


class AppViewModel: ViewModel() {

    private val mRepo: AppRepository by lazy { AppRepository() }

    private val _appList = MutableLiveData<List<AppModel>>()
    val appList: LiveData<List<AppModel>> = _appList

    fun saveAppData(model: AppModel) {
        viewModelScope.launch {
            mRepo.saveAppData(model)
        }
    }

    fun getAllSavedAppList() {
        viewModelScope.launch {
            val data = mRepo.getAllSaveAppList()
            _appList.postValue(data)
        }
    }

    fun delete(model: AppModel) {
        viewModelScope.launch {
            mRepo.delete(model)
        }
    }

}