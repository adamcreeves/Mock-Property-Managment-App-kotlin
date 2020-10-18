package com.example.mockpropertymanagmentapp.data.repositories

import android.content.Context
import android.view.View
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
    fun getData(myContext: Context, adapterTodoList: AdapterTodoList, myList: ArrayList<Task>, keyList: ArrayList<String>) {
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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

}