package com.baszczyk.mercpiggibank3.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.baszczyk.mercpiggibank3.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this).get(MoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMoreBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter()

        // Inflate the layout for this fragment
        return binding.root
    }

}
