package com.example.marketing.admins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketing.ViewModelFactory
import com.example.marketing.database.AppDao
import com.example.marketing.database.AppDatabase
import com.example.marketing.database.Repository
import com.example.marketing.databinding.FragmentAdminBinding
import com.example.marketing.forms.FormAdapter
import com.example.marketing.forms.FormViewModel

class AdminFragment: Fragment() {
    private lateinit var binding: FragmentAdminBinding
    private lateinit var viewModel: AdminFragmentVM
    private lateinit var serviceAdapter: FormAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater)
        val dao: AppDao = AppDatabase.getInstance(requireContext()).groupsDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AdminFragmentVM::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }
    private fun observeViewModel(viewModel: FormViewModel) {
        viewModel.forms.observe(viewLifecycleOwner, {
            it.let { serviceAdapter.notifyData(it) }
        })
    }

    private fun setupRecyclerView() {
        binding.serviceRv.layoutManager = LinearLayoutManager(context)
        binding.serviceRv.adapter = serviceAdapter
    }

}