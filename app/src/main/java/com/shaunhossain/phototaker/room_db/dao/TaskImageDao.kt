package com.shaunhossain.phototaker.room_db.dao;

import androidx.room.*
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskImageDao {

    @Query("SELECT * FROM TaskImageTable WHERE TicketNumber =:ticket_number")
    fun getImagePosition(ticket_number: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM TaskImageTable WHERE taskId =:task_id AND ImagePosition = :position")
    fun getImageList(task_id: String?, position: Int?): Flow<List<TaskImage>>

    @Query("SELECT * FROM TaskImageTable WHERE taskId =:task_id")
    fun getImage(task_id: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM TaskImageTable WHERE taskId =:task_id")
    fun getImageDB(task_id: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM TaskImageTable WHERE taskId =:task_id AND TicketNumber =:ticket_number")
    fun getImageByTypeDB(task_id: String?, ticket_number: String?): Flow<List<TaskImage>>

    @Query("SELECT * FROM TaskImageTable")
    fun getAllImages(): Flow<List<TaskImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(taskImage: TaskImage)

    @Query("DELETE FROM TaskImageTable WHERE taskId = :task_id AND ImagePosition =:position")
    suspend fun delete(task_id: String?, position: Int?)

    @Delete
    suspend fun deleteTask(taskImage: TaskImage)

    @Query("DELETE FROM TaskImageTable")
    suspend fun deleteAll()

    @Query("UPDATE TaskImageTable SET ImageFilePath=:file_path WHERE taskId = :task_id AND ImagePosition =:position")
    suspend fun updatePath(task_id: String?, file_path: String?, position: Int?)

    @Query("UPDATE TaskImageTable SET  ImagePosition =:position WHERE taskId = :task_id AND ImageFilePath=:file_path")
    suspend fun updatePosition(task_id: String?, file_path: String?, position: Int?)

    @Query("UPDATE TaskImageTable SET  taskId =:task_id WHERE TicketNumber =:ticket_number")
    suspend fun updateTaskId(task_id: String?, ticket_number: String?)
}