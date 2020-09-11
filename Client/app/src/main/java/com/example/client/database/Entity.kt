package com.example.client.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.client.model.Owner
import com.example.client.model.RepoDetail
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorites_table")
@Parcelize
data class RepoDBEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    val name: String,
    val full_name: String,
    @Embedded
    val owner: OwnerDBEntity,
    val description: String,
    val stargazers_count: Int,
    val language: String,
    val forks_count: Int,
    val forks: Int,
) : Parcelable

@Entity(tableName = "owner_table")
@Parcelize
data class OwnerDBEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "owner_id")
    val id: Int,
    val login: String,
    val avatar_url: String,
) : Parcelable

fun RepoDetail.mapToDBEntity(): RepoDBEntity {
    return RepoDBEntity(
        id, name, full_name,
        OwnerDBEntity(owner.id, owner.login, owner.avatar_url),
        description, stargazers_count, language, forks_count, forks
    )
}

fun RepoDBEntity.mapToRepoDetail(): RepoDetail {
    return RepoDetail(
        id, name, full_name,
        Owner(owner.id, owner.login, owner.avatar_url),
        description, stargazers_count, language, forks_count, forks
    )
}

fun MutableList<RepoDBEntity>.mapToReposList(): MutableList<RepoDetail> {
    return map { it.mapToRepoDetail() } as MutableList<RepoDetail>
}
