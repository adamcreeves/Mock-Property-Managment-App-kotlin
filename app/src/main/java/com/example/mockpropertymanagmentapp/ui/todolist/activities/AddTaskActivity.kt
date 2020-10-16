package com.example.mockpropertymanagmentapp.ui.todolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Task
import com.example.mockpropertymanagmentapp.helpers.toastShort
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.app_bar.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        firebaseDatabase = FirebaseDatabase.getInstance()
        init()
    }

    private fun init() {
        toolbar()
        button_add_task_submit.setOnClickListener {
            var priority = edit_text_priority.text.toString()
            var summary =edit_text_summary.text.toString()
            var dueDate = edit_text_due_date.text.toString()
            var estimatedCost = edit_text_estimated_cost.text.toString()
            var actualCost = edit_text_actual_cost.text.toString()
            var status = edit_text_status.text.toString()
            if(summary != "" && dueDate != "") {
                var task = Task(priority, summary, dueDate, estimatedCost, actualCost, status)
                var databaseReference = firebaseDatabase.getReference("tasks")
                var taskId = databaseReference.push().key
                databaseReference.child(taskId!!).setValue(task)
                this.toastShort("Task added successfully")
                finish()
            } else this.toastShort("You have to write a summary and due date")
        }
    }

    private fun toolbar() {
        var toolbar = toolbar
        toolbar.title = "Add New Task"
        setSupportActionBar(toolbar)
    }
}