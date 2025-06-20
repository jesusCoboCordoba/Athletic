package com.politecnico.athleticapp.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.politecnico.athleticapp.R
import java.util.concurrent.TimeUnit

class ChatAdapter(
    private val messages: MutableList<ChatMessage>,
    private val onLikeClicked: (ChatMessage) -> Unit
) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ShapeableImageView = view.findViewById(R.id.avatar_image_view)
        val senderTextView: TextView = view.findViewById(R.id.sender_text_view)
        val timestampTextView: TextView = view.findViewById(R.id.timestamp_text_view)
        val messageTextView: TextView = view.findViewById(R.id.message_text_view)
        val likeIcon: ImageView = view.findViewById(R.id.like_icon)
        val likeCountTextView: TextView = view.findViewById(R.id.like_count_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.senderTextView.text = message.sender
        holder.messageTextView.text = message.text
        holder.likeCountTextView.text = message.likes.toString()

        if (message.avatarResId != 0) {
            holder.avatar.setImageResource(message.avatarResId)
        } else {
            holder.avatar.setImageResource(R.drawable.profile_johanna) // Default avatar
        }

        holder.timestampTextView.text = getFormattedTimestamp(message.timestamp)

        holder.likeIcon.setOnClickListener {
            onLikeClicked(message)
            // Optimistically update the UI
            message.likes++
            holder.likeCountTextView.text = message.likes.toString()
            holder.likeIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.red_heart))
        }

        // Reset heart color based on a better logic if you track who liked what
        // For now, this is a simple visual feedback
        holder.likeIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray))

    }

    override fun getItemCount() = messages.size

    fun updateMessages(newMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    private fun getFormattedTimestamp(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        if (days > 0) return "$days days ago"
        
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        if (hours > 0) return "$hours hours ago"
        
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        if (minutes > 0) return "$minutes min ago"

        return "just now"
    }
} 