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

    fun saveUserToFirebase(nickname: String, imageUrl: String?) {
        firebaseModel.fetchUserFromFirebase()
        val currentUser = firebaseModel.userLiveData.value

        if (currentUser != null) {
            var updatedUser = currentUser.copy(nickname = nickname)

            if (imageUrl !== null) {
                 updatedUser = updatedUser.copy(imageUrl = imageUrl)
            }

            firebaseModel.saveUserToFirebase(updatedUser)
            saveUserToRoom(updatedUser)
        }
    }
}
