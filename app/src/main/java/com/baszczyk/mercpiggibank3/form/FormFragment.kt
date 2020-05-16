package com.baszczyk.mercpiggibank3.form

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.Mercedes
import com.baszczyk.mercpiggibank3.database.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.database.User
import com.baszczyk.mercpiggibank3.databinding.FragmentFormBinding

class FormFragment : Fragment() {

    lateinit var mercedes: Mercedes
    lateinit var formViewModel: FormViewModel
    lateinit var piggy: PiggyBank
    lateinit var binding: FragmentFormBinding
    lateinit var currentUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate<FragmentFormBinding>(
           inflater, R.layout.fragment_form, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = FormViewModelFactory(dataSource, application)

        formViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(FormViewModel::class.java)

        binding.formViewModel = formViewModel

        //binding.setLifecycleOwner(this)

        binding.nextButton.setOnClickListener {view: View ->
            createMercedes()
            formViewModel.addMercedes(mercedes)

            Handler().postDelayed({
                formViewModel.mercedesId()
                Handler().postDelayed({
                    val mercId = formViewModel.currentMercedes.value
                    piggy = PiggyBank(mercedesId = mercId!!, userId = 1, actualAmount = mercedes.price)
                    formViewModel.addPiggyBank(piggy)

                    view.findNavController().navigate(
                        FormFragmentDirections.actionFormFragmentToPiggiBankFragment())
                }, 500)
            }, 500)

//            va
//
        }
        return binding.root
    }

    private fun createMercedes() {
        mercedes = Mercedes(surname = binding.surnameInput.text.toString(),
            price = binding.PriceInput.text.toString().toDouble(),
            version = binding.VersionInput.text.toString(),
            engineCapacity = binding.engineCapacityInput.text.toString(),
            enginePower = binding.enginePowerInput.text.toString())
    }

    fun showDialog() {
       val dialog: AlertDialog.Builder? = activity?.let {
           AlertDialog.Builder(it)
       }
        dialog?.setMessage("utworzono poprawnie")

        val builer = dialog?.show()
    }
}
