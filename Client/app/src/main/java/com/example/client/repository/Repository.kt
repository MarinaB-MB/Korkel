package com.example.client.repository

import android.util.Log
import com.example.client.database.RepoDao
import com.example.client.database.mapToDBEntity
import com.example.client.database.mapToReposList
import com.example.client.model.Commits
import com.example.client.model.GithubRepo
import com.example.client.model.RepoDetail
import com.example.client.model.RepositoryModel
import com.example.client.network.GithubAPI
import com.example.client.utils.DataState
import com.example.client.utils.GITHUB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val api: GithubAPI, private val rd: RepoDao) {

    suspend fun getReposFromApi(
    ): Flow<DataState<List<RepositoryModel>>> = flow {
        try {
            emit(DataState.Loading)
            val reposFromApi = api.getRepos()
            emit(DataState.Success(reposFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    suspend fun getFavoritesRepos(): Flow<DataState<List<RepoDetail>>> = flow {
        Log.e("TAG", "getFavoritesRepos: ${rd.getAllRepos().size} ")
        try {
            emit(DataState.Loading)
            val repoFromDB = rd.getAllRepos().mapToReposList()
            emit(DataState.Success(repoFromDB))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    suspend fun addToFavorites(repoDetail: RepoDetail) {
        rd.addRepo(repoDetail.mapToDBEntity())
    }

    suspend fun deleteFromFavorites(repoDetail: RepoDetail) {
        rd.deleteRepo(repoDetail.mapToDBEntity())
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

    fun getReposFromGitHubOwner(
        page: Int = 1,
        perPage: Int = 100
    ): Flow<DataState<List<GithubRepo>>> = flow {
        try {
            emit(DataState.Loading)
            val githubRepoFromApi = api.getReposFromGitHubOwner(GITHUB, page, perPage)
            emit(DataState.Success(githubRepoFromApi))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}
