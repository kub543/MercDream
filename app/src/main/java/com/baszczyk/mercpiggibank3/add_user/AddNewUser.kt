package com.baszczyk.mercpiggibank3.add_user

import androidx.lifecycle.ViewModelProviders
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
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.baszczyk.mercpiggibank3.R
import com.baszczyk.mercpiggibank3.database.PiggyDatabase
import com.baszczyk.mercpiggibank3.database.entities.User
import com.baszczyk.mercpiggibank3.databinding.AddNewUserFragmentBinding
import kotlinx.android.synthetic.main.add_new_user_fragment.*

class AddNewUser : Fragment() {

    private lateinit var viewModel: AddNewUserViewModel
    private lateinit var binding: AddNewUserFragmentBinding
    private lateinit var addNewUser: EditText
    private lateinit var addNewUserPassword: EditText
    private lateinit var addNewUserEmail: EditText

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val addNewUserInput = addNewUser.text.toString().trim()
            val addNewUserPasswordInput = addNewUserPassword.text.toString().trim()
            val addNewUserEmailInput = addNewUserEmail.text.toString().trim()

            button_create_user.isEnabled = addNewUserInput.isNotEmpty()
                    && addNewUserPasswordInput.isNotEmpty() && addNewUserEmailInput.isNotEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.add_new_user_fragment, container, false
        )
        addNewUser = binding.newUserName
        addNewUserEmail = binding.addNewUserEmail
        addNewUserPassword = binding.newUserPassword

        addNewUser.addTextChangedListener(textWatcher)
        addNewUserPassword.addTextChangedListener(textWatcher)
        addNewUserEmail.addTextChangedListener(textWatcher)

        val application = requireNotNull(this.activity).application
        val dataSource = PiggyDatabase.getInstance(application).piggyDatabaseDao
        val viewModelFactory = AddNewUserFactory(dataSource, application)

        //binding.addNewUserViewModel = viewModel

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddNewUserViewModel::class.java)

        binding.buttonCreateUser.setOnClickListener { view: View ->
            addNewUser = binding.newUserName
            addNewUserPassword = binding.newUserPassword
            addNewUserEmail = binding.addNewUserEmail

            viewModel.addNewUser(createNewUser())
            viewModel.getNewUser()

            Handler().postDelayed({
                Toast.makeText(
                    this.context, "dodano urzytkownika ${viewModel.currentUser.value?.name}",
                    Toast.LENGTH_LONG
                ).show()
                view.findNavController().navigate(R.id.action_addNewUser_to_loggingFragment)
            }, 500)

        }

        return binding.root
    }

    private fun createNewUser(): User {
       return User(
            name = addNewUser.text.toString(),
            password = addNewUserPassword.text.toString(),
            email = addNewUserEmail.text.toString()
        )
    }

    }

