package com.rubayat.taskmanagerapp

import Database.Task
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rubayat.taskmanagerapp.Add_Task.Companion.editkey
import com.rubayat.taskmanagerapp.databinding.ActivityDetailsBinding

@Suppress("DEPRECATION")
class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    companion object{
        const val details = "Details"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tasks = intent.getParcelableExtra<Task>(details)

        tasks?.let {
            binding.apply {
                titleTv.setText(it.title)
                descriptionTv.setText(it.description)
                dueDateTv.text ="Due Date: ${it.dueDate}"
                cmplt.isChecked = it.isDone

            }
        }





    }
}