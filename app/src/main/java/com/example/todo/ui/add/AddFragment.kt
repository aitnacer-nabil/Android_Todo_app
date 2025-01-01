package com.example.todo.ui.add

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.TodoData
import com.example.todo.databinding.FragmentAddBinding
import com.example.todo.viewmodels.MainViewModel


@Suppress("DEPRECATION")
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.spinner.onItemSelectedListener = mainViewModel.listener

        //Set Menu Host
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_todo_menu, menu)

                val saveMenuItem = menu.findItem(R.id.action_save)
                val saveIcon = saveMenuItem.icon
                saveIcon?.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white), PorterDuff.Mode.SRC_IN)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.action_save -> {
                        val title = binding.editTextText.text.toString()
                        val description = binding.editTextMultipleLine.text.toString()
                        val  priority = binding.spinner.selectedItem.toString()
                        if (!mainViewModel.verifyDataFromUser(title,description)){
                            return false
                        }
                        val todo = TodoData(0,title,description,Priority.valueOf(priority))
                        mainViewModel.insertData(todo)
                        Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_addFragment_to_listFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return root
    }


}