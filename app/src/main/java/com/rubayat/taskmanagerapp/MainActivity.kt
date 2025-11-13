package com.rubayat.taskmanagerapp

import com.rubayat.taskmanagerapp.TaskAdapter
import Database.Task
import com.rubayat.taskmanagerapp.TaskDao
import com.rubayat.taskmanagerapp.TaskDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.rubayat.taskmanagerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskAdapter.handelUserClick {

    lateinit var binding: ActivityMainBinding
    private lateinit var dao : TaskDao
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbroom = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "Task_DB"
        ).allowMainThreadQueries().build()
        dao = dbroom.getTaskDao()

        binding.addTaskBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, Add_Task::class.java)
            startActivity(intent)
        }
        loadTask()
    }

    fun loadTask() {
        val taskList = dao.getAllTask()
        taskAdapter = TaskAdapter(taskList,this)
        binding.taskList.adapter = taskAdapter

    }
    override fun onResume() {
        super.onResume()
        loadTask()
    }

    override fun editClick(task: Task) {
        val editIntent = Intent(this@MainActivity, Add_Task::class.java)
        editIntent.putExtra(Add_Task.editkey,task)
        startActivity(editIntent)

    }

    override fun deleteClick(task: Task) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Task")
            setMessage("Are you sure you want to delete this task?")
            setPositiveButton("Yes") { _, _ ->
                dao.deletetask(task)
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                loadTask()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    override fun detailClick(task: Task) {
        val detailIntent= Intent(this@MainActivity, DetailsActivity::class.java)
        detailIntent.putExtra(DetailsActivity.details,task)
        startActivity(detailIntent)
    }


}