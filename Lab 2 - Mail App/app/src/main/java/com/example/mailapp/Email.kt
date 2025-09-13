package com.example.mailapp

data class Email(
    val sender: String,
    val title: String,
    val summary: String,
    val date: String,
    val isUnread: Boolean = false
)
