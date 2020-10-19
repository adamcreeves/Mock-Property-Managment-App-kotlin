package com.example.mockpropertymanagmentapp.ui.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.operation.ListenComplete
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_adapter_properties.view.*
import kotlinx.android.synthetic.main.row_adapter_todo_list.view.*
import java.text.FieldPosition

class AdapterTodoList(
    private var mContext: Context,
    private var mList: ArrayList<Task>,
    private var keyList: ArrayList<String>
) :
    RecyclerView.Adapter<AdapterTodoList.mViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_adapter_todo_list, parent, false)
        return mViewHolder(view)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        val task = mList[position]
        holder.bind(task, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: ArrayList<Task>) {
        notifyDataSetChanged()
        mList = list
    }

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, position: Int) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("tasks")
            var status = task.status
            if (status == "Completed") {
                itemView.image_view_completed_task.visibility = View.VISIBLE
            }
            itemView.text_view_priority.text = task.priority
            itemView.text_view_summary.text = task.summary
            itemView.text_view_due_date.text = task.dueDate
            itemView.text_view_estimated_cost.text = task.estimatedCost
            itemView.text_view_estimated_duration.text = task.estimatedDuration
            itemView.text_view_status.text = task.status
            Picasso.get().load(task.image)
                .resize(70, 70)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(itemView.image_view_task_image)
            itemView.button_delete_task.setOnClickListener {
                var builder = AlertDialog.Builder(mContext)
                builder.setTitle("Confirm Delete")
                builder.setMessage("Are you sure you want to delete this task?")
                builder.setNegativeButton(
                    "No"
                ) { dialog, p1 -> dialog?.dismiss() }
                builder.setPositiveButton(
                    "Yes"
                ) { p0, p1 ->
                    databaseReference.child(keyList[position]).setValue(null)
                    Toast.makeText(mContext, "Task deleted", Toast.LENGTH_SHORT).show()
                }
                var myAlertDialog = builder.create()
                myAlertDialog.show()
            }
            itemView.button_mark_task_complete.setOnClickListener {
                if (status == "Incomplete") {
                    itemView.image_view_completed_task.visibility = View.VISIBLE
                    databaseReference.child(keyList[position]).child("status").setValue("Completed")
                } else {
                    itemView.image_view_completed_task.visibility = View.INVISIBLE
                    databaseReference.child(keyList[position]).child("status")
                        .setValue("Incomplete")
                }
            }
        }
    }
}