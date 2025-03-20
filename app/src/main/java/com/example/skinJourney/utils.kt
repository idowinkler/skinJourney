package com.example.skinJourney

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.skinJourney.api.RetrofitClient
import com.example.skinJourney.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

object utils {

    fun getBitmapIfChanged(imageView: ImageView?, defaultDrawableRes: Int, context: Context): Bitmap? {
        val drawable = imageView?.drawable ?: return null

        if (drawable is BitmapDrawable) {
            val currentBitmap = drawable.bitmap
            if (currentBitmap.width == 0 || currentBitmap.height == 0) return null

            val defaultDrawable = ContextCompat.getDrawable(context, defaultDrawableRes)
            if (defaultDrawable is BitmapDrawable) {
                if (currentBitmap.sameAs(defaultDrawable.bitmap)) return null
            }
            return currentBitmap
        }
        return null
    }

    fun convertBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    fun analyzeSkinFromBitmap(apiKey: String, originalBitmap: Bitmap, callback: (String) -> Unit) {
        val base64Image = convertBitmapToBase64(originalBitmap)

        if (base64Image.isEmpty()) {
            Log.e("GeminiUtils", "Base64 conversion failed. Exiting function.")
            callback("Image encoding failed.")
            return
        }

        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(
                            text = """
                            You are a dermatologist AI. 
                            Analyze the skin condition of this image.
                            Identify acne, redness, dryness, or wrinkles.
                            If no face is detected, reply: "No face detected."
                            If the image is blurry, reply: "Unclear image, try again."
                            You MUST return a response, even if uncertain.
                            Don't talk about higher resolution or your difficulty analyzing. Just the analysis please.
                        """.trimIndent()
                        ),
                        Part(inline_data = InlineData(mime_type = "image/jpeg", data = base64Image))
                    )
                )
            )
        )

        RetrofitClient.apiService.analyzeImage(apiKey, request)
            .enqueue(object : Callback<GeminiResponse> {
                override fun onResponse(call: Call<GeminiResponse>, response: Response<GeminiResponse>) {
                    Log.d("GeminiUtils", "HTTP Response Code: ${response.code()}")
                    Log.d("GeminiUtils", "Full Response: ${response.raw()}")
                    Log.d("GeminiUtils", "Body: ${response.body()}")

                    val output = response.body()?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

                    val finalResult = when {
                        !response.isSuccessful -> "API Error: ${response.code()} ${response.message()}"
                        output == null -> "Gemini could not analyze the image."
                        output.trim().isEmpty() -> "AI did not generate a response."
                        else -> output
                    }

                    Log.d("GeminiUtils", "API Response: $finalResult")
                    callback(finalResult)
                }

                override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                    Log.e("GeminiUtils", "Request failed: ${t.message}")
                    callback("Request failed: ${t.message}")
                }
            })
    }

}
