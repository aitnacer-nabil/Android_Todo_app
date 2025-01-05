package com.example.todo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.IClickListener
import com.example.todo.data.models.Priority
import com.example.todo.data.models.TodoData
import com.example.todo.databinding.RowItemBinding

class ListAdapter(val clickListener: IClickListener): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var todoList = emptyList<TodoData>()
    class MyViewHolder(private val binding : RowItemBinding,private val clickListener: IClickListener): RecyclerView.ViewHolder(binding.root){

        fun bind(todoData: TodoData){
            binding.todoData = todoData
            when(todoData.priority){
                Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(binding.root.context.getColor(android.R.color.holo_red_light))
                 Priority.LOW-> binding.priorityIndicator.setCardBackgroundColor(binding.root.context.getColor(android.R.color.holo_orange_light))
                Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(binding.root.context.getColor(android.R.color.holo_green_light))
            }
            binding.rowContainer.setOnClickListener {
                clickListener.onClick(todoData)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding = RowItemBinding.inflate(view,parent,false)
        return MyViewHolder(binding,clickListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.bind(currentItem)


    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todoData: List<TodoData>){

        this.todoList = todoData
        notifyDataSetChanged()
    }
}