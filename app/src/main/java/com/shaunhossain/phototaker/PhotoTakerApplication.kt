package com.shaunhossain.phototaker

import android.app.Application
import com.shaunhossain.phototaker.repository.TaskRepository
import com.shaunhossain.phototaker.room_db.database.AppDatabase

class PhotoTakerApplication: Application() {

    private val database by lazy { AppDatabase.getDataBase(this) }
    val repository by lazy { TaskRepository(database.taskImageDao()) }

}