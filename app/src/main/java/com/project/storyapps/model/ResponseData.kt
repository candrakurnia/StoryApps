package com.project.storyapps.model

data class ResponseData(
    val error: Boolean,
    val message: String,
    val loginResult: LoginResult
)
