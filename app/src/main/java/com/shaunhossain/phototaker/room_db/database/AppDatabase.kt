package com.shaunhossain.phototaker.room_db.database;

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shaunhossain.phototaker.room_db.dao.TaskImageDao
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import java.util.concurrent.Executors


@Database(version = 1, entities = [TaskImage::class], exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun taskImageDao(): TaskImageDao?

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_image_bd"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}