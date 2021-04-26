package com.example.marketing.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.marketing.ViewModelFactory
import com.example.marketing.database.AppDao
import com.example.marketing.database.AppDatabase
import com.example.marketing.database.Repository
import com.example.marketing.database.Users
import com.example.marketing.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInFragmentVM
    private var users:List<Users> = mutableListOf()

    private val args by navArgs<SignInFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        val dao: AppDao = AppDatabase.getInstance(requireContext()).groupsDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SignInFragmentVM::class.java)
        binding.viewModel = viewModel
        observeViewModel(viewModel)
        binding.lifecycleOwner = this
        attachListeners()
        return binding.root
    }

    private fun attachListeners() {
        binding.confirmButton.setOnClickListener {
            if (binding.passwordEditText.text.toString()== users[0].password) {
                if (binding.passwordEditText.text.toString()=="admin"){
                    findNavController().navigate(
                        SignInFragmentDirections.main("admin")
                    )
                } else {
                    findNavController().navigate(
                        SignInFragmentDirections.main("user")
                    )
                }

            } else {
                Toast.makeText(requireContext(), "Невірний пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun observeViewModel(viewModel: SignInFragmentVM) {
        viewModel.users.observe(viewLifecycleOwner, {
            users = it.filter {
                it.phone==args.phone
            }
        })
    }
}