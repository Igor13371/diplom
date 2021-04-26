package com.example.marketing.forms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marketing.databinding.FragmentAdminBinding
import com.example.marketing.databinding.FragmentFormBinding

class Formfragment : Fragment(){
    private lateinit var binding: FragmentFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFormBinding.inflate(inflater)
        return binding.root
    }
}