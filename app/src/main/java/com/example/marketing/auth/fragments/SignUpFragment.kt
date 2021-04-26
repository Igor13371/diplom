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
import com.example.marketing.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpFragmentVM
    private val args by navArgs<SignUpFragmentArgs>()

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
        attachListeners()
        return binding.root
    }

    private fun attachListeners() {
        binding.confirmButton.setOnClickListener {
            if (binding.passwordEdit.text.toString() == binding.passwordEdit2.text.toString()) {
                if (binding.passwordEdit.text.toString() == "admin") {
                    viewModel.insert(
                        Users(
                            phone = args.phone,
                            password = binding.passwordEdit.text.toString(),
                            role = "admin"
                        )
                    )
                    findNavController().navigate(
                        SignInFragmentDirections.main("admin")
                    )
                } else {
                    viewModel.insert(
                        Users(
                            phone = args.phone,
                            password = binding.passwordEdit.text.toString(),
                            role = "user"
                        )
                    )
                    findNavController().navigate(
                        SignInFragmentDirections.main("user")
                    )
                }

            } else {
                Toast.makeText(requireContext(), "Паролі не співпадають", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
