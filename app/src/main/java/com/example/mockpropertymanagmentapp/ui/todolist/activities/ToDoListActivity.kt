package com.example.mockpropertymanagmentapp.ui.todolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Task
import com.example.mockpropertymanagmentapp.data.repositories.ToDoRepository
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.example.mockpropertymanagmentapp.ui.todolist.adapters.AdapterTodoList
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_to_do_list.*
import kotlinx.android.synthetic.main.app_bar.*

class ToDoListActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    var adapterTodoList: AdapterTodoList? = null
    var mList: ArrayList<Task> = ArrayList()
    var keyList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        databaseReference = FirebaseDatabase.getInstance().getReference(Task.COLLECTION_NAME)
        init()
    }

    private fun init() {
        toolbar()
        adapterTodoList = AdapterTodoList(this, mList, keyList)
        ToDoRepository().getData(this, adapterTodoList!!, mList, keyList)
        recycler_view_todo_list.layoutManager = LinearLayoutManager(this)
        recycler_view_todo_list.adapter = adapterTodoList
        progress_bar_todo.visibility = View.GONE
        button_todo_list_to_add_task.setOnClickListener{
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "To Do List"
        setSupportActionBar(toolbar)
    }

}