package com.example.skinJourney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinJourney.model.Post
import com.example.skinJourney.model.PostWithUser
import com.example.skinJourney.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel constructor(
    private val repository: PostRepository
) : ViewModel() {
    val userPosts: LiveData<List<PostWithUser>> = repository.userPostsLiveData
    val otherUsersPosts: LiveData<List<PostWithUser>> = repository.otherUsersPostsLiveData

    fun fetchPosts() {
        viewModelScope.launch {
            repository.fetchPostsFromFirebase()
        }
    }

    fun addPost(post: Post) {
        viewModelScope.launch {
            repository.addPost(post)
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }

    fun observeUserUpdates() {
        repository.observeUserUpdates()
    }
}
