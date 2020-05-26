package com.baszczyk.mercpiggibank3.main

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val listViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListViewModel::class.java)


        binding.listViewModel = listViewModel

        val adapter = PiggyBankAdapter(PiggyBankListener { piggyId ->
            listViewModel.onPiggyBankClicked(piggyId)
        })
        binding.piggyList.adapter = adapter
        val currentUserId = activity?.intent?.extras?.get("id").toString().toLong()
        listViewModel.allPiggies(currentUserId)

        binding.fab.setOnClickListener { view: View ->
            view.findNavController().navigate(
                ListFragmentDirections.actionListFragmentToFormFragment())
        }

        Handler().postDelayed({
                    if (listViewModel.piggies.value?.isEmpty()!!) {
                        binding.withoutPiggies.visibility = View.VISIBLE
                        binding.addNewPiggyButton.setOnClickListener { view: View ->
                            view.findNavController().navigate(
                                ListFragmentDirections.actionListFragmentToFormFragment()
                            )
                        }
                    } else {

                        listViewModel.piggies.observe(viewLifecycleOwner, Observer {
                            it?.let {
                                adapter.submitList(it)
                            }
                        })
                        binding.lifecycleOwner = this

                        listViewModel.navigateToPiggyDetails.observe(this, Observer { piggy ->
                            piggy?.let {
                                this.findNavController().navigate(
                                    ListFragmentDirections.actionListFragmentToPiggiBankFragment(
                                        piggy
                                    )
                                )
                                listViewModel.onPiggyDetailNavigated()
                            }
                        })

                        val manager = GridLayoutManager(
                            activity, 2,
                            GridLayoutManager.VERTICAL, false
                        )
                        binding.piggyList.layoutManager = manager
                    }
        }, 500)

        return binding.root
    }
}


