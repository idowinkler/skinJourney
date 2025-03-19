package com.example.skinJourney.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.storage.storage

class FirebaseModel {
    private val database = Firebase.firestore
    private val storage = Firebase.storage
    private val auth: FirebaseAuth = Firebase.auth

    init {
        val setting = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }

        database.firestoreSettings = setting
    }

    // Register a new user and store their nickname
    fun register(email: String, password: String, nickname: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val userMap = hashMapOf(
                            "uid" to userId,
                            "email" to email,
                            "nickname" to nickname
                        )
                        database.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener {
                                callback(true, null)
                            }
                            .addOnFailureListener { e ->
                                callback(false, e.message)
                            }
                    } else {
                        callback(false, "User ID is null")
                    }
                } else {
                    callback(false, task.exception?.message)
                    Log.d("ERR", task.exception?.message.toString())
                }
            }
    }

    // Login existing user
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    // Logout user
    fun logout() {
        auth.signOut()
    }

    fun get_current_user(): String? {
        return auth.currentUser?.uid
    }
}