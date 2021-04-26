package com.example.marketing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketing.database.Repository
import com.example.marketing.forms.FormViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FormViewModel::class.java)){
            return FormViewModel(repository)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}