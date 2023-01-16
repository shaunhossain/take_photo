package com.shaunhossain.phototaker.room_db.dao;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import com.shaunhossain.phototaker.utils.taskImageTable
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskImageDao {

    @Query("SELECT * FROM $taskImageTable WHERE TicketNumber =:ticket_number")
    fun getImagePosition(ticket_number: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM $taskImageTable WHERE taskId =:task_id AND ImagePosition = :position")
    fun getImageList(task_id: String?, position: Int?): Flow<List<TaskImage>>

    @Query("SELECT * FROM $taskImageTable WHERE taskId =:task_id")
    fun getImage(task_id: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM $taskImageTable WHERE taskId =:task_id")
    fun getImageDB(task_id: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM $taskImageTable WHERE taskId =:task_id AND TicketNumber =:ticket_number")
    fun getImageByTypeDB(task_id: String?, ticket_number: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM $taskImageTable")
    fun getAllImages(): Flow<List<TaskImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(taskImage: TaskImage)

    @Query("DELETE FROM $taskImageTable WHERE taskId = :task_id AND ImagePosition =:position")
    suspend fun delete(task_id: String?, position: Int?)

    @Query("DELETE FROM $taskImageTable WHERE taskId = :task_id")
    suspend fun deleteTask(task_id: String?)

    @Query("DELETE FROM $taskImageTable")
    suspend fun deleteAll()

    @Query("UPDATE $taskImageTable SET ImageFilePath=:file_path WHERE taskId = :task_id AND ImagePosition =:position")
    suspend fun updatePath(task_id: String?, file_path: String?, position: Int?)

    @Query("UPDATE $taskImageTable SET  ImagePosition =:position WHERE taskId = :task_id AND ImageFilePath=:file_path")
    suspend fun updatePosition(task_id: String?, file_path: String?, position: Int?)

    @Query("UPDATE $taskImageTable SET  TaskId =:task_id WHERE TicketNumber =:ticket_number")
    suspend fun updateTaskId(task_id: String?, ticket_number: String?)
}