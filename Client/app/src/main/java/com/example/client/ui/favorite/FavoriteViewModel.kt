package com.example.client.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.model.RepoDetail
import com.example.client.repository.Repository
import com.example.client.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {
    private val mRepos = MutableLiveData<DataState<List<RepoDetail>>>()
    val repos: LiveData<DataState<List<RepoDetail>>>
        get() = mRepos

    init {
        getFavoritesRepos()
    }


    fun getFavoritesRepos() {
        viewModelScope.launch {
            repository.getFavoritesRepos().onEach { dataState -> subscribeData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribeData(dataState: DataState<List<RepoDetail>>) {
        when (dataState) {
            is DataState.Loading -> {
                mRepos.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mRepos.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mRepos.postValue(DataState.Success(dataState.data))
            }
        }
    }

    fun addToDB(repoDetail: RepoDetail) {
        viewModelScope.launch {
            repository.addToFavorites(repoDetail)
        }
    }

    fun deleteFromDB(repoDetail: RepoDetail) {
        viewModelScope.launch {
            repository.deleteFromFavorites(repoDetail)
        }
    }
}