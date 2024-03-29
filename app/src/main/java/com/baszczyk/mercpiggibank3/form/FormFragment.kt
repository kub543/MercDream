package com.baszczyk.mercpiggibank3.form

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.baszczyk.mercpiggibank3.ExstrasMessages
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.entities.Mercedes
import com.baszczyk.mercpiggibank3.database.entities.PiggyBank
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.FragmentFormBinding
//import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.runBlocking

class FormFragment : Fragment() {

    lateinit var mercedes: Mercedes
    lateinit var formViewModel: FormViewModel
    lateinit var piggy: PiggyBank
    lateinit var binding: FragmentFormBinding
    lateinit var price: EditText

    val formTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputPrice= price.text.toString().trim()
            binding.nextButton.isEnabled = inputPrice.isNotEmpty()
        }
    }

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
        price = binding.PriceInput
        price.addTextChangedListener(formTextWatcher)

        //binding.setLifecycleOwner(this)

        binding.nextButton.setOnClickListener {view: View ->
            createMercedes()
            runBlocking {
                formViewModel.addMercedes(mercedes)
                formViewModel.mercedesId()
                createPiggy()
                formViewModel.addPiggyBank(piggy)
                showDialog()
                view.findNavController().navigate(
                    FormFragmentDirections.actionFormFragmentToListFragment())
            }

            //Handler().postDelayed({

                //Handler().postDelayed({

              //  }, 500)
           // }, 500)

        }

//        val notificationManager = ContextCompat.getSystemService(
//            app,
//            NotificationManager::class.java
//        ) as NotificationManager
//        notificationManager.sendNotification(app.getString(R.string.timer_running), app)
//        createChannel(getString(R.string.piggy_notification_channel_id),
//                        getString(R.string.piggy_notification_channel_name))

        return binding.root
    }

    private fun createPiggy() {
        val mercId = formViewModel.currentMercedes.value
        val mercSurname = mercedes.surname
        val currentUserId = activity?.intent?.extras?.get(ExstrasMessages.USER_ID).toString().toLong()
        piggy = PiggyBank(
            mercedesId = mercId!!, piggyName = mercSurname,
            userId = currentUserId, actualAmount = mercedes.price
        )

    }

    private fun createMercedes() {
        mercedes = Mercedes(
            surname = binding.surnameInput.text.toString(),
            price = binding.PriceInput.text.toString().toDouble(),
            version = binding.VersionInput.text.toString(),
            engineCapacity = binding.engineCapacityInput.text.toString(),
            enginePower = binding.enginePowerInput.text.toString()
        )
    }

    private fun showDialog() {
       val dialog: AlertDialog.Builder? = activity?.let {
           AlertDialog.Builder(it)
       }
        dialog?.setTitle("Utworzono poprawnie")
        dialog?.setPositiveButton("OK") { dialog, which ->
        }

        dialog?.show()
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Właśnie utworzyłeś nową skarbonkę, możesz coś do niej wrzucić"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
