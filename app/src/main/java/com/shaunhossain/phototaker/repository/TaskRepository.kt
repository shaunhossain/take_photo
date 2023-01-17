package com.shaunhossain.phototaker.repository

import androidx.annotation.WorkerThread
import com.shaunhossain.phototaker.room_db.dao.TaskImageDao
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskImageDao: TaskImageDao?) {
    var allTaskImage: Flow<List<TaskImage>> = taskImageDao!!.getAllImages()

    @WorkerThread
    suspend fun insertTaskImage(taskImage: TaskImage){
        taskImageDao!!.insertAll(taskImage)
    }

    @WorkerThread
    suspend fun updateTaskImage(task_id: String?, file_path: String?, position: Int?){
        taskImageDao!!.updatePath(task_id,file_path,position)
    }

    @WorkerThread
    suspend fun deleteTaskImage(taskImage: TaskImage){
        taskImageDao!!.deleteTask(taskImage)
    }

    @WorkerThread
    suspend fun deleteAllTaskImage(){
        taskImageDao!!.deleteAll()
    }
}