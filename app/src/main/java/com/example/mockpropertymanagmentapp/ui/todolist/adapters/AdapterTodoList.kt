package com.example.mockpropertymanagmentapp.ui.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mockpropertymanagmentapp.R
import com.example.mockpropertymanagmentapp.data.models.Task
import kotlinx.android.synthetic.main.row_adapter_todo_list.view.*
import java.text.FieldPosition

class AdapterTodoList(
    private var mContext: Context,
    private var mList: ArrayList<Task>,
    private var keyList: ArrayList<String>
) :
    RecyclerView.Adapter<AdapterTodoList.mViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_adapter_todo_list, parent, false)
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
            itemView.text_view_priority.text = task.priority
            itemView.text_view_summary.text = task.summary
            itemView.text_view_due_date.text = task.dueDate
            itemView.text_view_estimated_cost.text = task.estimatedCost
            itemView.text_view_actual_cost.text = task.actualCost
            itemView.text_view_status.text = task.status
        }
    }
}