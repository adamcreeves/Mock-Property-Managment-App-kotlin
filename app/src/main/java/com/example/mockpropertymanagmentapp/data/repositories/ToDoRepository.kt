package com.example.mockpropertymanagmentapp.data.repositories

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mockpropertymanagmentapp.data.models.Task
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.properties.adapters.AdapterProperties
import com.example.mockpropertymanagmentapp.ui.todolist.adapters.AdapterTodoList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoRepository {
    var databaseReference = FirebaseDatabase.getInstance().getReference(Task.COLLECTION_NAME)
    var firebaseDatabase = FirebaseDatabase.getInstance()
    fun getData(myContext: Context, adapterTodoList: AdapterTodoList, myList: ArrayList<Task>, keyList: ArrayList<String>) {
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myList.clear()
                keyList.clear()
                for(data in snapshot.children) {
                    var task = data.getValue(Task::class.java)
                    var key = data.key
                    myList.add(task!!)
                    keyList.add(key!!)
                }
                adapterTodoList.setData(myList)
            }
            override fun onCancelled(error: DatabaseError) {
                myContext.toastShort("An error has occurred")
            }
        })
    }
    fun addNewTask(priority: String, summary: String, dueDate: String, estimatedCost: String, estimatedDuration: String) {
        var newTask = Task(priority, summary, dueDate, estimatedCost, estimatedDuration)
        var databaseReference = firebaseDatabase.getReference("tasks")
        var taskId = databaseReference.push().key
        databaseReference.child(taskId!!).setValue(newTask)
    }

}