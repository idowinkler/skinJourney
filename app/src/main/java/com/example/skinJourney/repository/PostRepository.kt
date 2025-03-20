package com.example.skinJourney.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skinJourney.model.FirebaseModel
import com.example.skinJourney.model.dao.AppLocalDB
import com.example.skinJourney.model.Post
import com.example.skinJourney.model.PostWithUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor() {
    private val postDao = AppLocalDB.db.postDao()
    private val firebaseModel = FirebaseModel()

    private val _userPostsLiveData = MutableLiveData<List<PostWithUser>>()
    val userPostsLiveData: LiveData<List<PostWithUser>> get() = _userPostsLiveData

    private val _otherUsersPostsLiveData = MutableLiveData<List<PostWithUser>>()
    val otherUsersPostsLiveData: LiveData<List<PostWithUser>> get() = _otherUsersPostsLiveData

    init {
        fetchPostsFromFirebase()
    }

    fun fetchPostsFromFirebase() {
        firebaseModel.fetchPostsWithUserDetails()
        firebaseModel.postsLiveData.observeForever { postsWithUsers ->
            postsWithUsers?.let {
                val userId = firebaseModel.getCurrentUser() ?: return@observeForever

                val sortedPosts = it.sortedByDescending { post -> post.timestamp ?: 0L }

                val userPosts = sortedPosts.filter { post -> post.userId == userId }
                val otherUsersPosts = sortedPosts.filter { post -> post.userId != userId }

                _userPostsLiveData.postValue(userPosts)
                _otherUsersPostsLiveData.postValue(otherUsersPosts)

                savePostsToRoom(sortedPosts.map { postWithUser ->
                    Post(
                        uid = postWithUser.id,
                        description = postWithUser.description,
                        imageUrl = postWithUser.imageUrl,
                        aiAnalysis = postWithUser.aiAnalysis,
                        userId = postWithUser.userId
                    )
                })
            }
        }
    }

    private fun savePostsToRoom(posts: List<Post>) {
        CoroutineScope(Dispatchers.IO).launch {
            posts.forEach { postDao.insertPost(it) }
        }
    }

    fun addPost(post: Post) {
        val postWithTimestamp = post.copy(timestamp = System.currentTimeMillis())
        firebaseModel.addPostToFirebase(postWithTimestamp)
        savePostToRoom(postWithTimestamp)
    }

    fun updatePost(post: Post) {
        firebaseModel.updatePostInFirebase(post)
        savePostToRoom(post)
    }

    fun deletePost(post: Post) {
        firebaseModel.deletePostFromFirebase(post)
        CoroutineScope(Dispatchers.IO).launch {
            postDao.deletePost(post)
        }
    }

    private fun savePostToRoom(post: Post) {
        CoroutineScope(Dispatchers.IO).launch {
            postDao.insertPost(post)
        }
    }

    fun observeUserUpdates() {
        firebaseModel.userLiveData.observeForever { user ->
            user?.let {
                fetchPostsFromFirebase()
            }
        }
    }
}
