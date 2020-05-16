package com.baszczyk.mercpiggibank3.logging

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.databinding.AddNewUserFragmentBinding
import com.baszczyk.mercpiggibank3.databinding.FragmentLoggingBinding
import kotlinx.android.synthetic.main.fragment_logging.*
import org.w3c.dom.Text
import java.util.*


class LoggingFragment : Fragment() {

    private lateinit var viewModel: LoggingViewModel
    private lateinit var binding: FragmentLoggingBinding
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText

     val loggingTextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

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
                viewModel.getUserPassword(binding.userName.text.toString())
                Handler().postDelayed({
                    if (isCorrectPassword())  {
                        Toast.makeText(this.context, "poprawne hasło", Toast.LENGTH_LONG).show()
                        viewModel.getUser(userName.text.toString())
                        Handler().postDelayed({
                            viewModel.currentUser?.value!!
                            viewModel.getAllPiggies(viewModel.currentUser.value?.userId!!)
                            Handler().postDelayed({
                                val list = viewModel.piggies
                                if (list.isEmpty()) {
                                    view.findNavController().navigate(
                                        LoggingFragmentDirections.actionLoggingFragmentToMainFragment())
                                } else {
                                    view.findNavController().navigate(
                                        LoggingFragmentDirections.actionLoggingFragmentToListFragment())
                                }
                            }, 500)

                        },500)

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
