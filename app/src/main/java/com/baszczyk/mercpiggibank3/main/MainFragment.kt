package com.baszczyk.mercpiggibank3.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater, R.layout.fragment_main, container, false)

        binding.addNewPiggyButton.setOnClickListener { view : View ->
            view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToFormFragment())
        }

        return binding.root
    }

}
