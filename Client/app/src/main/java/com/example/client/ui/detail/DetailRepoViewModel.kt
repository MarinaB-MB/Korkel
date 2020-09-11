package com.example.client.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.model.Commits
import com.example.client.model.RepoDetail
import com.example.client.repository.Repository
import com.example.client.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailRepoViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val repo = MutableLiveData<DataState<RepoDetail>>()
    val repoDetail: LiveData<DataState<RepoDetail>>
        get() = repo

    private val commits = MutableLiveData<DataState<List<Commits>>>()
    val commitsRepo: LiveData<DataState<List<Commits>>>
        get() = commits

    fun getRepo(fulName: String) {
        viewModelScope.launch {
            repository.getRepoByName(fulName = fulName)
                .onEach { dataState -> subscribeDataDetail(dataState) }.launchIn(viewModelScope)
        }
    }

    fun getCommits(fulName: String) {
        viewModelScope.launch {
            repository.getRepoCommits(fulName = fulName).onEach { dataState ->
                subscribeData(dataState)
            }.launchIn(viewModelScope)
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

    private fun subscribeData(dataState: DataState<List<Commits>>) {
        when (dataState) {
            is DataState.Loading -> {
                commits.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                commits.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                commits.postValue(DataState.Success(dataState.data))
            }
        }
    }
}