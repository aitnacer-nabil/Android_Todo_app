package com.example.todo.viewmodels

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoDatabase
import com.example.todo.data.models.TodoData
import com.example.todo.data.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val database = TodoDatabase.getDatabase(application)
    private val todoDao = database.getTodoDao()
    private var databaseRepository : DatabaseRepository = DatabaseRepository(todoDao)
    val getAllData = databaseRepository.getAllData
    val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 -> {
                    Toast.makeText(application,"High Priority Selected", Toast.LENGTH_SHORT).show()
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,android.R.color.holo_red_dark))
                }
                1 -> {
                    Toast.makeText(application,"Medium Priority Selected", Toast.LENGTH_SHORT).show()
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,android.R.color.holo_green_dark))
                }
                2 -> {
                    Toast.makeText(application,"Low Priority Selected", Toast.LENGTH_SHORT).show()
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,android.R.color.holo_orange_dark))
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Toast.makeText(application,"Nothing Selected", Toast.LENGTH_SHORT).show()
        }


    }
    fun insertData(todoData: TodoData) {
      viewModelScope.launch(Dispatchers.IO) {
            databaseRepository.insertData(todoData)
      }
    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        if(title.isEmpty()){
            Toast.makeText(getApplication(),"Please enter a title", Toast.LENGTH_SHORT).show()
            return false
        }
        if(description.isEmpty()){
            Toast.makeText(getApplication(),"Please enter a description", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }



}