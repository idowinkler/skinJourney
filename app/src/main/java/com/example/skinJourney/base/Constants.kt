package com.example.skinJourney.base

import com.example.skinJourney.model.Post


typealias PostsCallback = (List<Post>) -> Unit
typealias EmptyCallback = () -> Unit

object Constants {

    object COLLECTIONS {
        const val POSTS = "posts"
    }
}