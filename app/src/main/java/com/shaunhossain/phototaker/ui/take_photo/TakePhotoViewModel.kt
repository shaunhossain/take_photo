package com.shaunhossain.phototaker.ui.take_photo

import androidx.lifecycle.*
import com.shaunhossain.phototaker.repository.TaskRepository
import com.shaunhossain.phototaker.room_db.entity.TaskImage
import kotlinx.coroutines.launch

class TakePhotoViewModel(private val repository: TaskRepository) : ViewModel() {

    var taskImages: LiveData<List<TaskImage>> = repository.allTaskImage.asLiveData()

    fun addTaskImage(taskImage: TaskImage) = viewModelScope.launch {
        repository.insertTaskImage(taskImage)
    }
}

class TaskImageModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TakePhotoViewModel::class.java))
            return TakePhotoViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}