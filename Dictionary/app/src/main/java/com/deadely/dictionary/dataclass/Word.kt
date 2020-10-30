package com.deadely.dictionary.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "words_table")
@Parcelize
data class Word(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val word: String,
    val description: String
) : Parcelable
