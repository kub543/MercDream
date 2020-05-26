package com.baszczyk.mercpiggibank3.logging

import android.content.Intent
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
import com.baszczyk.mercpiggibank3.MainActivity
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.FragmentLoggingBinding
import kotlinx.android.synthetic.main.fragment_logging.*

class LoggingFragment : Fragment() {

    private lateinit var viewModel: LoggingViewModel
    private lateinit var binding: FragmentLoggingBinding
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText

     val loggingTextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userInputName = userName.text.toString().trim()
            val userInputPassword = userPassword.text.toString().trim()

            loggingButton.isEnabled = userInputName.isNotEmpty() && userInputPassword.isNotEmpty()
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentLoggingBinding>(
            inflater, R.layout.fragment_logging, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = LoggingViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(LoggingViewModel::class.java)
        viewModel.getAllUsersNames()

        userName = binding.userName
        userPassword = binding.userPassword

        userName.addTextChangedListener(loggingTextWatcher)
        userPassword.addTextChangedListener(loggingTextWatcher)

        binding.loggingButton.setOnClickListener { view: View ->
            userName = binding.userName
            userPassword = binding.userPassword

            if (isUserInDatabased()) {
                viewModel.getUserPassword(userName.text.toString())
                Handler().postDelayed({
                    if (isCorrectPassword())  {
                        viewModel.getUser(userName.text.toString())

                            Handler().postDelayed({

                                val userId = viewModel.currentUser.value!!.userId.toString()
                                val intent = Intent(activity, MainActivity::class.java).apply {
                                    putExtra("id", userId)
                                }
                                activity?.startActivity(intent)
                            }, 500)

                    } else {
                        binding.wrongDate.text = "niepoprawne hasło"
                    }
                }, 500)

            } else {
                view.findNavController().navigate(R.id.action_loggingFragment_to_addNewUser)
            }
        }

        binding.newUserButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_loggingFragment_to_addNewUser)
        }

        return binding.root
    }

    private fun isUserInDatabased() = viewModel.users.contains(userName.text.toString())

    private fun isCorrectPassword() = userPassword.text.toString() == viewModel.userPassword
}