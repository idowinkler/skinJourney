package com.example.skinJourney.model

import android.graphics.Bitmap
import android.util.Log
import com.example.skinJourney.base.EmptyCallback
import com.example.skinJourney.base.PostsCallback
import com.example.skinJourney.utils.analyzeSkinFromBitmap
import com.idz.colman24class2.model.CloudinaryModel
import java.util.logging.Logger

class Model private constructor() {

    enum class Storage {
        FIREBASE,
        CLOUDINARY
    }

//    private val firebaseModel = FirebaseModel()
    private val cloudinaryModel = CloudinaryModel()

    companion object {
        val shared = Model()
    }

    fun getAllProgressPosts(callback: PostsCallback) {
//        firebaseModel.getAllStudents(callback)
        val samplePosts = listOf(
            Post("1", "user1", "A scenic landscape", "https://picsum.photos/600/400?random=1"),
            Post("2", "user2", "A modern architecture", "https://picsum.photos/600/400?random=2"),
            Post("3", "user3", "A beautiful nature shot", "https://picsum.photos/600/400?random=3"),
            Post("4", "user4", "A cozy evening view", "https://picsum.photos/600/400?random=4"),
            Post("5", "user5", "A city skyline", "https://picsum.photos/600/400?random=5"),
            Post("6", "user6", "A starry night", "https://picsum.photos/600/400?random=6"),
            Post("7", "user7", "An abstract art piece", "https://picsum.photos/600/400?random=7"),
            Post("8", "user8", "A peaceful countryside", "https://picsum.photos/600/400?random=8"),
            Post("9", "user9", "A misty morning", "https://picsum.photos/600/400?random=9"),
            Post("10", "user10", "A warm sunset", "https://picsum.photos/600/400?random=10")
        )

        callback(samplePosts.shuffled())
    }

    fun addPost(post: Post, postImage: Bitmap?, storage: Storage, callback: EmptyCallback) {
        postImage?.let {
            uploadImageToCloudinary(
                            image = it,
                           onSuccess = { url ->
                               Log.i("TAG", url)    // Info log
//                               val st = student.copy(avatarUrl = url)
//                               firebaseModel.add(st, callback)
                               analyzeSkinFromBitmap("AIzaSyCvFczlE2yq1hR5z1p-NKicEfdPRkurPKM", postImage) { result ->
                                   Log.d("SkinAnalysis", "API for skinnnnn: $result") // ✅ LOGGING RESPONSE
                               }
                               callback()
                           },
                           onError = { callback() }
                       )
        }
//        firebaseModel.add(student) {
//            profileImage?.let {
//
//                when (storage) {
//                    Storage.FIREBASE -> {
//                        uploadImageToFirebase(
//                            image = it,
//                            name = student.id) { url ->
//                            url?.let {
//                                val st = student.copy(avatarUrl = it)
//                                firebaseModel.add(st, callback)
//                            } ?: callback()
//                        }
//                    }
//                    Storage.CLOUDINARY -> {
//                        uploadImageToCloudinary(
//                            image = it,
//                            name = student.id,
//                            onSuccess = { url ->
//                                val st = student.copy(avatarUrl = url)
//                                firebaseModel.add(st, callback)
//                            },
//                            onError = { callback() }
//                        )
//                    }
//                }
//            } ?: callback()
        }

    private fun uploadImageToFirebase(image: Bitmap, name: String, callback: (String?) -> Unit) {
//        firebaseModel.uploadImage(image, name, callback)
    }

     fun uploadImageToCloudinary(image: Bitmap, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        cloudinaryModel.uploadBitmap(
            bitmap = image,
            onSuccess = onSuccess,
            onError = onError
        )
    }
}



