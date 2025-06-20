package com.politecnico.athleticapp.ui.community

data class ChatMessage(
    var id: String = "",
    val text: String = "",
    val sender: String = "",
    val timestamp: Long = 0,
    var likes: Int = 0,
    val avatarResId: Int = 0
) 