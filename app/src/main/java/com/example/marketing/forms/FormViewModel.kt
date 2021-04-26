package com.example.marketing.forms

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.database.Offer
import com.example.marketing.database.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FormViewModel(private val formRepository: Repository) :
    ViewModel(), Observable {

    val forms = formRepository.offers

    fun insert(offer: Offer): Job = viewModelScope.launch {
        formRepository.insertOffer(offer)
    }
    fun nukeTable():Job = viewModelScope.launch {
        formRepository.nukeTable()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}