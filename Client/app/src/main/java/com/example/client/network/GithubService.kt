package com.example.client.network

import com.example.client.model.Commits
import com.example.client.model.GithubRepo
import com.example.client.model.RepoDetail
import com.example.client.model.RepositoryModel
import com.example.client.utils.GET_REPO
import com.example.client.utils.GET_REPOS
import com.example.client.utils.GET_REPOS_GITHUB
import com.example.client.utils.GET_REPO_COMMITS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {


    @GET(GET_REPOS)
    suspend fun getRepos(
    ): List<RepositoryModel>

    @GET(GET_REPOS_GITHUB)
    suspend fun getReposFromGitHubOwner(
        @Path("org") org: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 100
    ): List<GithubRepo>

    @GET(GET_REPO)
    suspend fun getRepoDetail(@Path("user") user: String, @Path("repo") repo: String): RepoDetail

    @GET(GET_REPO_COMMITS)
    suspend fun getRepoCommits(
        @Path("user") user: String,
        @Path("repo") repo: String,
        @Query("per_page") pages: Int = 10
    ): List<Commits>
}