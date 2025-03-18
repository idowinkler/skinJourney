package com.example.skinJourney

import androidx.lifecycle.ViewModel
import com.example.skinJourney.model.Post

class ProgressViewModel : ViewModel() {
    private var _posts: List<Post>? = null
    var posts: List<Post>?
        get() = _posts
        private set(value) {
            _posts = value
        }

    fun set(posts: List<Post>?) {
        this.posts = posts
    }
}