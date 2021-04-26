package com.example.marketing.forms

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketing.R
import com.example.marketing.ViewModelFactory
import com.example.marketing.database.AppDao
import com.example.marketing.database.AppDatabase
import com.example.marketing.database.Offer
import com.example.marketing.database.Repository
import com.example.marketing.databinding.FragmentFormBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Formfragment : Fragment() {
    private lateinit var binding: FragmentFormBinding
    private lateinit var viewModel: FormViewModel
    private lateinit var formAdapter: FormAdapter

    private lateinit var preferences: SharedPreferences

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
        preferences = activity?.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)!!
        if (preferences.getString("ROLE", "") == "admin") forAdmin()
        else forUser()
        return binding.root
    }

    private fun forAdmin() {
        formAdapter = FormAdapter().also { formAdapter ->
            formAdapter.setOnItemClickListener { _, offer ->
                openDialogEdit(offer)
            }
        }
        setupRecyclerView()
        observeViewModel(viewModel)
        binding.addButton.setOnClickListener {
            openDialog()
        }
    }

    private fun forUser() {
        formAdapter = FormAdapter().also { formAdapter ->
            formAdapter.setOnItemClickListener { _, offer ->
                Toast.makeText(requireContext(), offer.name, Toast.LENGTH_SHORT).show()
            }
        }
        setupRecyclerView()
        observeViewModel(viewModel)
        binding.addButton.visibility = View.GONE
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

    private fun openDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_service, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Додавання нової послуги")
        val alertDialog = builder.show()
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            if (!dialogView.findViewById<TextInputEditText>(R.id.name_enter).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.description_enter).text.toString()
                    .isNullOrEmpty()
            ) {
                val name =
                    dialogView.findViewById<TextInputEditText>(R.id.name_enter).text.toString()
                val description =
                    dialogView.findViewById<TextInputEditText>(R.id.description_enter).text.toString()
                viewModel.insert(
                    Offer(
                        name = name,
                        description = description,
                        url = "https://sukhari.com.ua/wp-content/uploads/2019/08/digital-marketing-02.jpg"
                    )
                )
            } else {
                Toast.makeText(context, "Будь ласка, введіть данні", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }

    private fun openDialogEdit(offer: Offer) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_service, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Редагування послуги")
        val alertDialog = builder.show()
        dialogView.findViewById<TextInputEditText>(R.id.name_enter).setText(offer.name)
        dialogView.findViewById<TextInputEditText>(R.id.description_enter)
            .setText(offer.description)
        dialogView.findViewById<MaterialButton>(R.id.dismiss_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            if (!dialogView.findViewById<TextInputEditText>(R.id.name_enter).text.toString()
                    .isNullOrEmpty() ||
                !dialogView.findViewById<TextInputEditText>(R.id.description_enter).text.toString()
                    .isNullOrEmpty()
            ) {
                val name =
                    dialogView.findViewById<TextInputEditText>(R.id.name_enter).text.toString()
                val description =
                    dialogView.findViewById<TextInputEditText>(R.id.description_enter).text.toString()
                viewModel.updateOffer(
                    newName = name,
                    newDescription = description,
                    searchId = offer.id
                )
            } else {
                Toast.makeText(context, "Будь ласка, введіть данні", Toast.LENGTH_SHORT).show()
            }
            alertDialog.dismiss()
        }
    }
}