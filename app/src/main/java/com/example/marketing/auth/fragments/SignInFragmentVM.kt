package com.example.marketing.auth.fragments

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.example.marketing.database.Repository

class SignInFragmentVM(private val formRepository: Repository) :
    ViewModel(), Observable {

    val users = formRepository.users
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}