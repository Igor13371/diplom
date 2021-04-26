package com.example.marketing.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketing.ViewModelFactory
import com.example.marketing.database.AppDao
import com.example.marketing.database.AppDatabase
import com.example.marketing.database.Offer
import com.example.marketing.database.Repository
import com.example.marketing.databinding.FragmentFormBinding

class Formfragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel

    private val formAdapter = FormAdapter().also { formAdapter ->
        formAdapter.setOnItemClickListener { _, offer ->
            Toast.makeText(requireContext(), offer.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater)
        val dao: AppDao = AppDatabase.getInstance(requireContext()).groupsDAO
        val repository = Repository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupRecyclerView()
        observeViewModel(viewModel)
//        viewModel.insert(
//            Offer(
//                name = "Послуга 1",
//                description = "Гарний опис для гарних людей ахахаххахах",
//                url = "https://ivanpalii.com/wp-content/uploads/2016/10/marketing-future-com.png"
//            )
//        )
//        viewModel.insert(
//            Offer(
//                name = "Послуга 2",
//                description = "Гарний опис для гарних людей ахахаххахах",
//                url = "https://ivanpalii.com/wp-content/uploads/2016/10/marketing-future-com.png"
//            )
//        )
//        viewModel.insert(
//            Offer(
//                name = "Послуга 3",
//                description = "Гарний опис для гарних людей ахахаххахах",
//                url = "https://sukhari.com.ua/wp-content/uploads/2019/08/digital-marketing-02.jpg"
//            )
//        )
        return binding.root
    }

    private fun observeViewModel(viewModel: FormViewModel) {
        viewModel.forms.observe(viewLifecycleOwner, {
            it.let { formAdapter.notifyData(it) }
        })
    }

    private fun setupRecyclerView() {
        binding.formRv.layoutManager = LinearLayoutManager(context)
        binding.formRv.adapter = formAdapter
    }
}