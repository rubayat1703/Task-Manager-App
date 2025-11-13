package com.rubayat.taskmanagerapp

import Database.Task
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rubayat.taskmanagerapp.databinding.ItemListBinding

class TaskAdapter(val tasks: List<Task>,
                  val listener : handelUserClick,
                 ): RecyclerView.Adapter<ItemViewHolder>(){

                      interface handelUserClick{
                          fun editClick(task: Task)
                          fun deleteClick(task: Task)
                          fun detailClick(task: Task)
                      }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val usertask = tasks[position]
        holder.binding.apply {
            tvTaskTitle.text = usertask.title
            tvTaskDescription.text = usertask.description
            dueDateTv.text = "Due Date: ${usertask.dueDate}"
            cbTv.isChecked = usertask.isDone

            root.setOnClickListener {
                listener.detailClick(usertask)
            }



            deleteBtn.setOnClickListener {
               listener.deleteClick(usertask)
            }
            editBtn.setOnClickListener {
                listener.editClick(usertask)
            }

        }
    }
    override fun getItemCount(): Int {
       return tasks.size
    }

}