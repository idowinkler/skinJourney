package com.example.skinJourney.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Post(
    @PrimaryKey val id: String,
    val userId: String,
    val description: String,
    val imageUrl: String,
): Parcelable {

    companion object {
        private const val ID_KEY = "id"
        private const val IMAGE_URL_KEY = "imageUrl"
        private const val USER_ID_KEY = "userId"
        private const val DESCRIPTION_KEY = "description"

        fun fromJSON(json: Map<String, Any>): Post {
            val id = json[ID_KEY] as? String ?: ""
            val description = json[DESCRIPTION_KEY] as? String ?: ""
            val imageUrl = json[IMAGE_URL_KEY] as? String ?: ""

            return Post(
                id = id,
                userId = "ido",
                description = description,
                imageUrl = imageUrl)
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ID_KEY to id,
                USER_ID_KEY to userId,
                DESCRIPTION_KEY to description,
                IMAGE_URL_KEY to imageUrl)
        }
}
