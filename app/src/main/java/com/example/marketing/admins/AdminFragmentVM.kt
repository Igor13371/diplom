package com.example.marketing.admins

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.example.marketing.database.Repository

class AdminFragmentVM (private val formRepository: Repository) :
    ViewModel(), Observable {
    val chosenService = formRepository.service
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}