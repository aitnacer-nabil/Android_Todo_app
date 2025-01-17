package com.example.todo.ui.update

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.databinding.FragmentUpdateBinding
import com.example.todo.viewmodels.MainViewModel


@Suppress("DEPRECATION")
class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.currentEditTextText.setText(args.currentTodo.title)
        binding.currentEditTextMultipleLine.setText(args.currentTodo.description)
        binding.currentSpinner.setSelection(parsePriority(args.currentTodo.priority))
        binding.currentSpinner.onItemSelectedListener = mainViewModel.listener


        //Set Menu Host
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_todo_menu, menu)
                val saveMenuItem = menu.findItem(R.id.action_update)
                val saveIcon = saveMenuItem.icon
                saveIcon?.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white), PorterDuff.Mode.SRC_IN)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.action_update -> {
                        //Do Something
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



        return root

    }

    fun parsePriority(priority: Priority): Int {
        return when(priority){
           Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2


        }
    }


}