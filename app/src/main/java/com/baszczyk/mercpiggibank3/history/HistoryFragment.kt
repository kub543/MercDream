package com.baszczyk.mercpiggibank3.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.baszczyk.mercpiggibank3.MainActivity

import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    var isPiggyHistory = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = HistoryViewModelFactory(dataSource, application)

        historyViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HistoryViewModel::class.java)

        binding.historyViewModel = historyViewModel

        val adapter = DepositAdapter()
        binding.piggyHistory.adapter = adapter

        historyViewModel.deposit.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val args = activity?.intent?.extras?.get("isPiggy").toString().toBoolean()
        isPiggyHistory = args

        //optionsMenu
        if(isPiggyHistory) {
            val piggyId = activity?.intent?.extras?.get("piggy").toString().toLong()
            historyViewModel.piggyDepositId(piggyId)
        } else if (!isPiggyHistory) {
            //drowerLayout
            val userId = activity?.intent?.extras?.get("id").toString().toLong()
            historyViewModel.allDeposits(userId)
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.intent?.putExtra("isPiggy", false)
    }
}
