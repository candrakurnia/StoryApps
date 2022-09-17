package com.project.storyapps.model

data class ResponseStories(
    val error: Boolean,
    val message: String,
    val listStory: List<ListStory>
)
