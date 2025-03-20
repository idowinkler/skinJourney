package com.example.skinJourney.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.skinJourney.model.User
import com.example.skinJourney.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    val userLiveData: LiveData<User> = repository.userLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchUser() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.fetchUserFromFirebase()
            _isLoading.value = false
        }
    }

    fun updateUser(nickname: String) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.saveUserToFirebase(nickname)
            _isLoading.value = false
        }
    }
}
