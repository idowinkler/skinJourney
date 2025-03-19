package com.example.skinJourney.api

import com.example.skinJourney.model.GeminiRequest
import com.example.skinJourney.model.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1/models/gemini-1.5-pro:generateContent")
    fun analyzeImage(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): Call<GeminiResponse>
}
