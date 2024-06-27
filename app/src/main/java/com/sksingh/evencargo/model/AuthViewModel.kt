package com.sksingh.evencargo.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.sksingh.evencargo.data.Resource
import com.sksingh.evencargo.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
private val repository: AuthRepository
) :ViewModel() {

private val _loginflow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginflow:StateFlow<Resource<FirebaseUser>?> = _loginflow

    private val _signupflow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupflow:StateFlow<Resource<FirebaseUser>?> = _signupflow

    val currentUser:FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null){
            _loginflow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email:String,password:String) = viewModelScope.launch{
        _loginflow.value = Resource.Loading
        val result = repository.login(email,password)
        _loginflow.value = result
    }
    fun signup(name:String,email:String,password:String) = viewModelScope.launch{
        _signupflow.value = Resource.Loading
        val result = repository.signup(name,email,password)
        _signupflow.value = result
    }

    fun logout(){
        repository.logout()
        _loginflow.value = null
        _signupflow.value = null
    }

}