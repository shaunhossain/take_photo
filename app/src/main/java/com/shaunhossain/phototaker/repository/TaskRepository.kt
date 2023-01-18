package com.shaunhossain.phototaker.repository

import android.app.RecoverableSecurityException
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.IntentSenderRequest
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

    fun deleteFile(uri: Uri,context: Context){

        try{
            // android 28 and below
            context.contentResolver.delete(uri, null, null)
        }catch (e : SecurityException){
            // android 29 (Andriod 10)
            val intentSender = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    MediaStore.createDeleteRequest(context.contentResolver, listOf(uri)).intentSender
                }
                // android 30 (Andriod 11)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                    val recoverableSecurityException = e as? RecoverableSecurityException
                    recoverableSecurityException?.userAction?.actionIntent?.intentSender
                }
                else -> null
            }
            intentSender?.let { sender ->
                IntentSenderRequest.Builder(sender).build()
            }
        }
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