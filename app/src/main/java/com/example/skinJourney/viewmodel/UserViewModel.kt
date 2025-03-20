package com.example.skinJourney.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.skinJourney.model.User
import com.example.skinJourney.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    val userLiveData: LiveData<User> = repository.userLiveData

    fun fetchUser() {
        repository.fetchUserFromFirebase()
    }

    fun updateUser(nickname: String, imageUl: String?) {
        repository.saveUserToFirebase(nickname, imageUl)
    }
}
