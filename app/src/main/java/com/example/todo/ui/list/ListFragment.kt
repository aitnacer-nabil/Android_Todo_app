package com.example.todo.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.IClickListener
import com.example.todo.R
import com.example.todo.data.models.TodoData
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.ui.update.UpdateFragment
import com.example.todo.viewmodels.MainViewModel


class ListFragment : Fragment() , IClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val listAdapter: ListAdapter by lazy { ListAdapter(
        clickListener = this
    ) }

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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mainViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            listAdapter.setData(data)

        })

        //Set Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.group_sort -> {
                        //Do Something
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return root
    }
    //Menu

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(todoData: TodoData) {
    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(todoData)
        findNavController().navigate(action)
    }
}