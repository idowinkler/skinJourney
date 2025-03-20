package com.example.skinJourney.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.skinJourney.model.FirebaseModel
import com.example.skinJourney.model.dao.AppLocalDB
import com.example.skinJourney.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(context: Context) {
    private val userDao = AppLocalDB.db.userDao()
    private val firebaseModel = FirebaseModel()

    val userLiveData: LiveData<User> = userDao.getUser(firebaseModel.getCurrentUser() ?: "")

    fun fetchUserFromFirebase() {
        firebaseModel.fetchUserFromFirebase()
        firebaseModel.userLiveData.observeForever { user ->
            user?.let { saveUserToRoom(it) }
        }
    }

    private fun saveUserToRoom(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insertUser(user)
        }
    }

    // Update the nickname on Firebase and Room (only the nickname)
    fun saveUserToFirebase(nickname: String) {
        val currentUserId = firebaseModel.getCurrentUser() ?: return

        // Fetch the current user data from Room or Firebase
        firebaseModel.fetchUserFromFirebase()
        val currentUser = firebaseModel.userLiveData.value

        if (currentUser != null) {
            // Update the nickname field
            val updatedUser = currentUser.copy(nickname = nickname)

            // Save the updated user to Firebase
            firebaseModel.saveUserToFirebase(updatedUser)

            // Save the updated user to Room
            saveUserToRoom(updatedUser)
        }
    }
}
