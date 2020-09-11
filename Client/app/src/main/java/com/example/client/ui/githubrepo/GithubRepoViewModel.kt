package com.example.client.ui.githubrepo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.model.GithubRepo
import com.example.client.repository.Repository
import com.example.client.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GithubRepoViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val mGithubRepos = MutableLiveData<DataState<List<GithubRepo>>>()
    val githubRepos: LiveData<DataState<List<GithubRepo>>>
        get() = mGithubRepos


    init {
        getGithubRepos()
    }

    fun getGithubRepos() {
        viewModelScope.launch {
            repository.getReposFromGitHubOwner().onEach { dataState -> subscribeData(dataState) }
                .launchIn(viewModelScope)
        }
    }

    private fun subscribeData(dataState: DataState<List<GithubRepo>>) {
        when (dataState) {
            is DataState.Loading -> {
                mGithubRepos.postValue(DataState.Loading)
            }
            is DataState.Error -> {
                mGithubRepos.postValue(DataState.Error(dataState.exception))
            }
            is DataState.Success -> {
                mGithubRepos.postValue(DataState.Success(dataState.data))
            }
        }
    }
}