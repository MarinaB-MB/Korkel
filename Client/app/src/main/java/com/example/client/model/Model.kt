package com.example.client.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class RepositoryModel(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: Owner,
) : Parcelable

@Parcelize
data class Owner(
    val login: String,
    val id: Int,
    val avatar_url: String,
) : Parcelable

/////////////////////////

@Parcelize
data class License(
    val key: String,
    val name: String,
    val spdx_id: String,
    val url: String,
    val node_id: String
) : Parcelable

@Parcelize
data class RepoDetail(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: Owner,
    val html_url: String,
    val description: String,
    val stargazers_count: Int,
    val language: String,
    val forks_count: Int,
    val forks: Int,
    val open_issues: Int,
    val watchers: Int,
    val default_branch: String,
    val temp_clone_token: String,
    val network_count: Int,
    val subscribers_count: Int
) : Parcelable

@Parcelize
data class Commits(
    val sha: String,
    val commit: Commit,
    val author: Author,
    val committer: Committer?,
    val parents: List<Parents>
) : Parcelable {}


@Parcelize
data class Commit(
    val author: InnerAuthor,
    val committer: Committer?,
    val message: String,
) : Parcelable {}

@Parcelize
data class Author(
    val login: String,
    val id: Int,
    val avatar_url: String,
) : Parcelable

@Parcelize
data class InnerAuthor(
    val name: String,
    val email: String,
    val date: Date
) : Parcelable

@Parcelize
data class Committer(
    val login: String? = "",
    val id: Int,
    val avatar_url: String,
) : Parcelable {}

@Parcelize
data class Parents(
    val sha: String,
    val url: String,
    val html_url: String
) : Parcelable {}

@Parcelize
data class Tree(
    val sha: String,
    val url: String
) : Parcelable {}

@Parcelize
data class Verification(

    val verified: Boolean,
    val reason: String,
    val signature: String,
    val payload: String
) : Parcelable {}

//////
@Parcelize
data class GithubRepo(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: Owner,
) : Parcelable {}