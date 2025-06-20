package com.politecnico.athleticapp.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentCommunityChatBinding

class CommunityChatFragment : Fragment() {

    private var _binding: FragmentCommunityChatBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private val messagesRef = database.getReference("community-chat")
    private val messagesList = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    private val avatarPlaceholders = listOf(
        R.drawable.profile_johanna,
        R.drawable.group_1,
        R.drawable.group_2,
        R.drawable.group_3,
        R.drawable.group_4
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter(messagesList) { message ->
            onLikeClicked(message)
        }
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        binding.messagesRecyclerView.adapter = chatAdapter

        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        listenForMessages()
    }

    private fun listenForMessages() {
        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages = mutableListOf<ChatMessage>()
                snapshot.children.forEach { postSnapshot ->
                    val message = postSnapshot.getValue<ChatMessage>()
                    if (message != null) {
                        message.id = postSnapshot.key!!
                        newMessages.add(message)
                    }
                }
                messagesList.clear()
                messagesList.addAll(newMessages)
                chatAdapter.notifyDataSetChanged()
                binding.messagesRecyclerView.scrollToPosition(messagesList.size - 1)

                if (messagesList.isEmpty()) {
                    sendWelcomeMessage()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value, log or show error
            }
        })
    }

    private fun sendMessage() {
        val messageText = binding.messageEditText.text.toString().trim()
        if (messageText.isNotBlank()) {
            val senderName = "Ben White" // Placeholder for actual user
            val message = ChatMessage(
                text = messageText,
                sender = senderName,
                timestamp = System.currentTimeMillis(),
                avatarResId = getAvatarForSender(senderName)
            )

            val newMessageRef = messagesRef.push()
            message.id = newMessageRef.key!!
            newMessageRef.setValue(message)

            binding.messageEditText.text.clear()
        }
    }

    private fun sendWelcomeMessage() {
        val welcomeMessage = ChatMessage(
            text = "Welcome to the community chat! Feel free to ask anything.",
            sender = "Help Center",
            timestamp = System.currentTimeMillis(),
            likes = 15,
            avatarResId = R.drawable.ic_check_circle_24
        )
        val messageRef = messagesRef.push()
        welcomeMessage.id = messageRef.key!!
        messageRef.setValue(welcomeMessage)
    }

    private fun onLikeClicked(message: ChatMessage) {
        val newLikes = message.likes + 1
        messagesRef.child(message.id).child("likes").setValue(newLikes)
    }

    private fun getAvatarForSender(sender: String): Int {
        // Simple logic to assign a consistent avatar to a sender
        val hashCode = sender.hashCode()
        return avatarPlaceholders[hashCode % avatarPlaceholders.size]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 