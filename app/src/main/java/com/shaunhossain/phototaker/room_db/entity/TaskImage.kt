package com.shaunhossain.phototaker.room_db.entity;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shaunhossain.phototaker.utils.taskImageTable

@Entity(tableName = taskImageTable)
data class TaskImage(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "TaskId") var taskId: Int?,
    @ColumnInfo(name = "TicketNumber") var ticketNumber: String?,
    @ColumnInfo(name = "ImageFilePath") var imageFilePath: String?,
    @ColumnInfo(name = "ImagePosition") var imagePosition: Int
)

