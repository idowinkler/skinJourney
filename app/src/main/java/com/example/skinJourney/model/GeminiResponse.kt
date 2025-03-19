package com.example.skinJourney.model

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: ResponseContent? // Changed from output to content, as the response is inside content
)

data class ResponseContent(
    val parts: List<ResponsePart>?
)

data class ResponsePart(
    val text: String? // Text is inside parts, not directly in candidate
)