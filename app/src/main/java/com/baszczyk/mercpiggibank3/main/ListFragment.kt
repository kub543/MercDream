package com.baszczyk.mercpiggibank3.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.FragmentListBinding

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val listViewModel = ViewModelProviders.of(this, viewModelFactory)
                            .get(ListViewModel::class.java)


        binding.listViewModel = listViewModel
        val adapter = PiggyBankAdapter()
        binding.piggyList.adapter = adapter

        listViewModel.allPiggies(1)

        binding.fab.setOnClickListener { view: View ->
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToFormFragment())
        }

//        listViewModel.piggies.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.data = it
//            }
//        })

        listViewModel.piggies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        binding.lifecycleOwner = this


        //val manager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)



        return binding.root
    }
}
