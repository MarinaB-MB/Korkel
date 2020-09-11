package com.example.client.repository

import com.example.client.model.Commits
import com.example.client.model.GithubRepo
import com.example.client.model.RepoDetail
import com.example.client.model.RepositoryModel
import com.example.client.network.GithubAPI
import com.example.client.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val api: GithubAPI) {

    suspend fun getReposFromApi(): Flow<DataState<List<RepositoryModel>>> = flow {
        try {
            emit(DataState.Loading)
            val reposFromApi = api.getRepos()
            emit(DataState.Success(reposFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getFavoritesRepos() {

    }

    fun getAboutInfo(): String? {
        return "info"
    }

    fun getRepoByName(fulName: String): Flow<DataState<RepoDetail>> = flow {
        try {
            emit(DataState.Loading)
            val data = fulName.split("/")
            val repoFromApi = api.getRepoDetail(data[0], data[1])
            emit(DataState.Success(repoFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getRepoCommits(fulName: String): Flow<DataState<List<Commits>>> = flow {
        try {
            emit(DataState.Loading)
            val data = fulName.split("/")
            val commitsFromApi = api.getRepoCommits(data[0], data[1], 10)
            emit(DataState.Success(commitsFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getReposFromGitHubOwner(): Flow<DataState<List<GithubRepo>>> = flow {
        try {
            emit(DataState.Loading)
            val githubRepoFromApi = api.getReposFromGitHubOwner()
            emit(DataState.Success(githubRepoFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}
