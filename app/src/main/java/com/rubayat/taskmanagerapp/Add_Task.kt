package com.rubayat.taskmanagerapp


import com.rubayat.taskmanagerapp.TaskDao
import com.rubayat.taskmanagerapp.TaskDatabase
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.rubayat.taskmanagerapp.databinding.ActivityAddTaskBinding
import java.util.Calendar


@Suppress("DEPRECATION")
class Add_Task : AppCompatActivity() {
    companion object{
        const val editkey = "edit"
        const val Update = "UPDATE TASK"
        const val Add = "ADD TASK"
    }

    lateinit var binding : ActivityAddTaskBinding
    private lateinit var dao : TaskDao
    private var currentTaskId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dueDateEt.setOnClickListener {
            showDate()
        }


        val dbroom = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "Task_DB"
        ).allowMainThreadQueries().build()
        dao = dbroom.getTaskDao()

        if (intent.hasExtra(editkey)){
            binding.saveBtn.text = Update

            val tasks = intent.getParcelableExtra<Task>(editkey)

            tasks?.let {
                binding.apply {
                    titleEt.setText(it.title)
                    descriptionEt.setText(it.description)
                    dueDateEt.setText(it.dueDate)
                    completeEt.isChecked = it.isDone
                    currentTaskId = it.id

                }
            }

        }else{
            binding.saveBtn.text = Add
        }

        binding.saveBtn.setOnClickListener {
            val title = binding.titleEt.text.toString().trim()
            val description = binding.descriptionEt.text.toString().trim()
            val date = binding.dueDateEt.text.toString().trim()
            val isDone = binding.completeEt.isChecked

            if  (title.isEmpty() || description.isEmpty() || date.isEmpty())  {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.saveBtn.text==Add){
                
                addTask(title,description,date,isDone)
                
            }else{
                updateTask(title,description,date,isDone)
            }

        }

    }

    private fun updateTask(title: String, description: String, date: String, done: Boolean) {
        val tasks = Task(currentTaskId,title,description,date,done)
        dao.editTask(tasks)

        Toast.makeText(this, "Edit Successful!!!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addTask(title: String, description: String, date: String, isDone: Boolean) {
        val task = Task(0,title,description,date,isDone)
        dao.addTask(task)

        Toast.makeText(this,"Add Successful!!", Toast.LENGTH_SHORT).show()

        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showDate() {
        Calendar.getInstance().apply {
            DatePickerDialog(
                this@Add_Task,
                {_, year, month, date -> binding.dueDateEt.setText("%02d/%02d/%04d".format(date, month + 1, year)) },
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}




