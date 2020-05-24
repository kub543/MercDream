package com.baszczyk.mercpiggibank3.piggy

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.entities.Deposit
import com.baszczyk.mercpiggibank3.database.entities.Mercedes
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.FragmentPiggiBankBinding
import java.text.SimpleDateFormat
import java.util.*

class PiggiBankFragment : Fragment() {

    private lateinit var piggyBankViewModel: PiggyBankViewModel

   private lateinit var piggy: PiggyBank
   private lateinit var mercedes: Mercedes


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPiggiBankBinding>(
            inflater, R.layout.fragment_piggi_bank, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = PiggyBankViewModelFactory(dataSource, application)

        piggyBankViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PiggyBankViewModel::class.java)


        binding.piggyBankViewModel = piggyBankViewModel

        val args = PiggiBankFragmentArgs.fromBundle(requireArguments())
        val piggyId = args.piggyId

        piggyBankViewModel.piggyGet(piggyId)

        activity?.intent?.putExtra("piggy", piggyId)

        Handler().postDelayed({
            piggyBankViewModel.mercedesGet(piggyBankViewModel.piggy.value?.mercedesId!!)
        Handler().postDelayed({
            piggy = piggyBankViewModel.piggy.value!!
            mercedes = piggyBankViewModel.mercedes.value!!

            binding.apply {
                nameCar.text = mercedes.surname
                versionNumber.text = mercedes.version
                capasityEnginee.text = mercedes.engineCapacity
                powerEnginee.text = mercedes.enginePower
                actualPrice.text = piggy.actualAmount.toString()
            }

            if (piggy.actualAmount <= 0.0) {
                binding.inputAmount.visibility = View.GONE
            }

        }, 50)

        }, 50)

        binding.addDepositButton.setOnClickListener {
           val dateTime = getCurrentDateTime().toString()
           val deposit = binding.inputAmount.text.toString().toDouble()
           var actualAmount = piggy.actualAmount
           actualAmount -= deposit

            if(actualAmount <= 0.0) {
                showCongratulationAlert()
                getSound()
            }

            piggyBankViewModel.addDeposit(
                Deposit(
                    amount = deposit, data = dateTime,
                    mercedesId = mercedes.mercedesId, userId = piggy.userId,
                    piggyName = piggy.piggyName
                )
            )
            piggyBankViewModel.updatePiggyActualAmount(actualAmount, piggy.piggyId)

            binding.actualPrice.text = actualAmount.toString()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun showDeleteAlert() {
        val dialog: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        dialog?.setTitle("Czy chcesz usunąć skarbonkę: ${piggy.piggyName}")
        dialog?.setPositiveButton("TAK") { dialog, which ->
            piggyBankViewModel.deletePiggies(piggy.piggyId)
            view?.findNavController()?.navigate(R.id.action_piggiBankFragment_to_listFragment)
        }
        dialog?.setNegativeButton("NIE") { dialog, which ->
        }

        dialog?.show()
    }

    private fun showCongratulationAlert() {
        val dialog: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        dialog?.setTitle("Gratualcje! Właśnie uzbierałeś na Mercedesa: ${mercedes.surname}")
        dialog?.setPositiveButton("OK"){dialog, which ->
            view?.findNavController()?.navigate(R.id.action_piggiBankFragment_to_listFragment)
        }
        dialog?.show()
    }

    private fun getSound() {
        val mediaPlayer: MediaPlayer? = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer?.start()
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.piggy_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {
           R.id.delete_piggy -> showDeleteAlert()
           R.id.historyFragment -> { activity?.intent?.putExtra("isPiggy", true)
                                    NavigationUI.onNavDestinationSelected(item,
                                    view!!.findNavController())}
       }

        return super.onOptionsItemSelected(item)
    }
}