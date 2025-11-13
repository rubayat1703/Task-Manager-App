package com.rubayat.taskmanagerapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getAllTask(): List<Task>

    @Delete
    fun deletetask(task: Task)

    @Update
    fun editTask(task: Task)
}