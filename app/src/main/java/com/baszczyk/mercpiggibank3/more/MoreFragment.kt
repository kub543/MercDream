package com.baszczyk.mercpiggibank3.more

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.databinding.FragmentMoreBinding
import com.baszczyk.mercpiggibank3.network.MercedesApiFilter

class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by lazy {
        ViewModelProvider(this).get(MoreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoreBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (it != null) {
                this.findNavController().navigate(

                )
            }
        })

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when(item.itemId) {
                R.id.show_rent_menu -> MercedesApiFilter.SHOW_RENT
                R.id.show_buy_menu -> MercedesApiFilter.SHOW_BUY
                else -> MercedesApiFilter.SHOW_ALL
            }
        )
        return true
    }

}
