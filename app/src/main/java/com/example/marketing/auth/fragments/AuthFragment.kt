package com.example.marketing.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marketing.ViewModelFactory
import com.example.marketing.database.AppDao
import com.example.marketing.database.AppDatabase
import com.example.marketing.database.Repository
import com.example.marketing.database.Users
import com.example.marketing.databinding.FragmentAutintificationBinding
import com.example.marketing.forms.FormViewModel

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAutintificationBinding
    private lateinit var viewModel: AuthFragmentVM
    private var users:List<Users> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutintificationBinding.inflate(inflater)
        val dao: AppDao = AppDatabase.getInstance(requireContext()).groupsDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AuthFragmentVM::class.java)
        binding.viewModel = viewModel
        observeViewModel(viewModel)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.confirmButton.setOnClickListener {
            val filteredUsers = users.filter {
                it.phone  == binding.phoneEditText.text.toString()
            }
                if (!filteredUsers.isNullOrEmpty()) {
                    findNavController().navigate(
                        AuthFragmentDirections.signIn(binding.phoneEditText.text.toString())
                    )
                }
                else
                {
                    findNavController().navigate(
                        AuthFragmentDirections.signUp(binding.phoneEditText.text.toString())
                    )
                }
        }
    }
    private fun observeViewModel(viewModel: AuthFragmentVM) {
        viewModel.users.observe(viewLifecycleOwner, {
           users = it
        })
    }
}