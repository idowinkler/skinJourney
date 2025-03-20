package com.example.skinJourney.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.storage.storage

class FirebaseModel {
    private val database = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth
    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData
    private val _postsLiveData = MutableLiveData<List<PostWithUser>>()
    val postsLiveData: LiveData<List<PostWithUser>> get() = _postsLiveData

    init {
        val setting = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }

        database.firestoreSettings = setting
    }

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

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUser(): String? {
        return auth.currentUser?.uid
    }

    fun fetchUserFromFirebase() {
        val userId = getCurrentUser() ?: return
        database.collection("users").document(userId).get().addOnSuccessListener { snapshot ->
            val user = snapshot.toObject(User::class.java)
            _userLiveData.postValue(user)
        }.addOnFailureListener {
            Log.e("FirebaseRepository", "Error fetching user", it)
        }
    }

    fun saveUserToFirebase(user: User) {
        database.collection("users").document(user.uid).set(user)
    }

    fun fetchPostsWithUserDetails() {
        database.collection("posts").get()
            .addOnSuccessListener { snapshot ->
                val posts = snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
                if (posts.isNotEmpty()) {
                    val userIds = posts.map { it.userId }.distinct()

                    database.collection("users")
                        .whereIn("uid", userIds)
                        .get()
                        .addOnSuccessListener { userSnapshot ->
                            val usersMap = userSnapshot.documents.associateBy(
                                { it.id }, { it.toObject(User::class.java) }
                            )

                            val postsWithUsers = posts.map { post ->
                                val user = usersMap[post.userId]
                                PostWithUser(
                                    post.uid,
                                    post.description,
                                    post.imageUrl,
                                    post.userId,
                                    post.aiAnalysis,
                                    user?.nickname ?: "",
                                    user?.imageUrl ?: "",
                                    post.timestamp,
                                    )
                            }

                            _postsLiveData.postValue(postsWithUsers)
                        }
                        .addOnFailureListener {
                            Log.e("FirebaseModel", "Error fetching user details", it)
                        }
                }
            }
            .addOnFailureListener {
                Log.e("FirebaseModel", "Error fetching posts", it)
            }
    }


    fun addPostToFirebase(post: Post) {
        database.collection("posts").document(post.uid).set(post)
            .addOnSuccessListener {
                fetchPostsWithUserDetails() // Refresh posts list
            }
            .addOnFailureListener {
                Log.e("FirebaseModel", "Error adding post", it)
            }
    }

    fun updatePostInFirebase(post: Post) {
        database.collection("posts").document(post.uid).set(post)
            .addOnSuccessListener {
                fetchPostsWithUserDetails() // Refresh posts list
            }
            .addOnFailureListener {
                Log.e("FirebaseModel", "Error updating post", it)
            }
    }

    fun deletePostFromFirebase(post: Post) {
        database.collection("posts").document(post.uid).delete()
            .addOnSuccessListener {
                fetchPostsWithUserDetails() // Refresh posts list
            }
            .addOnFailureListener {
                Log.e("FirebaseModel", "Error deleting post", it)
            }
    }
}