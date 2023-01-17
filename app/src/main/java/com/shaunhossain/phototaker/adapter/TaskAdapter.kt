package com.shaunhossain.phototaker.adapter


import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shaunhossain.phototaker.R
import com.shaunhossain.phototaker.databinding.TaskItemViewBinding
import com.shaunhossain.phototaker.room_db.entity.TaskImage


class TaskAdapter(var dataList: List<TaskImage>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    class TaskViewHolder(private val binding: TaskItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(get: TaskImage) {
            val bundle = Bundle().apply {
                putSerializable("taskData", get)
            }
            binding.imageView.setImageURI(Uri.parse(get.imageFilePath))
            binding.imageView.setOnClickListener {
                binding.imageView.findNavController().navigate(R.id.action_FirstFragment_to_contentImagePreviewDialog,bundle)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(dataList[position])

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}