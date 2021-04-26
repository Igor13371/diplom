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
import com.example.marketing.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpFragmentVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        val dao: AppDao = AppDatabase.getInstance(requireContext()).groupsDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SignUpFragmentVM::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.confirmButton.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.main("admin")
            )
        }
        return binding.root
    }
}
