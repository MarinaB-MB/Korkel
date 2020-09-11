package com.example.client.ui.repos

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.model.RepoDetail
import com.example.client.model.RepositoryModel
import com.example.client.repository.Repository
import com.example.client.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ReposViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mRepos = MutableLiveData<DataState<List<RepositoryModel>>>()
    val repos: LiveData<DataState<List<RepositoryModel>>>
        get() = mRepos

    private val repo = MutableLiveData<DataState<RepoDetail>>()
    val repoDetail: LiveData<DataState<RepoDetail>>
        get() = repo

    init {
        getRepos()
    }

    fun getRepos() {
        viewModelScope.launch {
            repository.getReposFromApi()
                .onEach { dataState -> subscribeData(dataState) }.launchIn(viewModelScope)
        }
    }

    private fun subscribeData(dataState: DataState<List<RepositoryModel>>) {
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

    fun getRepoDetail(fullName: String) {
        viewModelScope.launch {
            repository.getRepoByName(fullName)
                .onEach { dataState -> subscribeDataDetail(dataState) }.launchIn(viewModelScope)
        }
    }

    private fun subscribeDataDetail(dataState: DataState<RepoDetail>) {
        when (dataState) {
            is DataState.Loading -> {
                repo.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                repo.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                repo.postValue(DataState.Success(dataState.data))
            }
        }
    }

    fun addToDb(data: RepoDetail) {
        viewModelScope.launch { repository.addToFavorites(data) }
    }

    fun deleteFromDb(data: RepoDetail) {
        viewModelScope.launch { repository.deleteFromFavorites(data) }
    }


}