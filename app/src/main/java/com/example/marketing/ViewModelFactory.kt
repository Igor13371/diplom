package com.example.marketing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marketing.admins.AdminFragmentVM
import com.example.marketing.auth.fragments.AuthFragmentVM
import com.example.marketing.auth.fragments.SignInFragmentVM
import com.example.marketing.auth.fragments.SignUpFragmentVM
import com.example.marketing.database.Repository
import com.example.marketing.forms.FormViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FormViewModel::class.java)){
            return FormViewModel(repository)as T
        }
        if(modelClass.isAssignableFrom(AuthFragmentVM::class.java)){
            return AuthFragmentVM(repository)as T
        }
        if(modelClass.isAssignableFrom(SignInFragmentVM::class.java)){
            return SignInFragmentVM(repository)as T
        }
        if(modelClass.isAssignableFrom(SignUpFragmentVM::class.java)){
            return SignUpFragmentVM(repository)as T
        }
        if(modelClass.isAssignableFrom(AdminFragmentVM::class.java)){
            return AdminFragmentVM(repository)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}