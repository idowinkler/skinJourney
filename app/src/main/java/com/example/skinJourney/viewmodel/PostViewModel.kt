package com.example.skinJourney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPosts() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.fetchPostsFromFirebase()
            _isLoading.value = false
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
