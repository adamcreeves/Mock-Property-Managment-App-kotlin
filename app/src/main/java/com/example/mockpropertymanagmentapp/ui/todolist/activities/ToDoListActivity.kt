package com.example.mockpropertymanagmentapp.ui.todolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mockpropertymanagmentapp.R
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        init()
    }

    private fun init() {
        button_todo_list_to_add_task.setOnClickListener{
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }
}