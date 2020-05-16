package com.baszczyk.mercpiggibank3.Piggi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.databinding.FragmentPiggiBankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PiggiBankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PiggiBankFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPiggiBankBinding>(
            inflater, R.layout.fragment_piggi_bank, container, false)

        return binding.root
    }
}
