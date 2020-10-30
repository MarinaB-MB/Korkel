package com.deadely.dictionary

import android.app.Application
import androidx.room.Room
import com.deadely.dictionary.database.AppDataBase
import com.deadely.dictionary.database.AppDataBase.Companion.DATABASE_NAME

class App : Application() {
    companion object {
        lateinit var instance: App private set
    }

    lateinit var db: AppDataBase
    override fun onCreate() {
        super.onCreate()
        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }
}
