package com.example.marketing.auth.fragments

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketing.database.Repository
import com.example.marketing.database.Users
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AuthFragmentVM(private val formRepository: Repository) :
    ViewModel(), Observable {

    val users = formRepository.users

    fun insert(users: Users): Job = viewModelScope.launch {
        formRepository.insertUser(users)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}