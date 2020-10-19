package com.example.mockpropertymanagmentapp.data.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mockpropertymanagmentapp.data.listeners.ToDoListener
import com.example.mockpropertymanagmentapp.data.repositories.ToDoRepository

class ToDoViewModel : ViewModel() {
    var priority: String? = null
    var summary: String? = null
    var dueDate: String? = null
    var estimatedCost: String? = null
    var estimatedDuration: String? = null
    var image: String? = "https://sm.pcmag.com/t/pcmag_uk/guide/t/the-best-t/the-best-to-do-list-apps-for-2020_fvu6.1200.jpg"
    var todoListener: ToDoListener? = null

    fun onSaveNewTaskClicked(view: View) {
        todoListener?.onStarted()
        if (summary.isNullOrEmpty() || estimatedCost.isNullOrEmpty()) {
            todoListener?.onFailedAdd("You need to enter a summary and estimated cost")
        }
        ToDoRepository().addNewTask(
            view.context,
            priority!!,
            summary!!,
            dueDate!!,
            estimatedCost!!,
            estimatedDuration!!,
            image!!
        )
        todoListener?.onSuccessfulAdd("New Task Added Successfully")
    }
}