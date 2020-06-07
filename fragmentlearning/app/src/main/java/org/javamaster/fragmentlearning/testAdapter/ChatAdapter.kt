package org.javamaster.fragmentlearning.testAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.ChatMessage

/**
 * @author yudong
 * @date 2019/8/25
 */
class ChatAdapter(private val messageList: List<ChatMessage>) : RecyclerView.Adapter<ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messageList[position]
        if (message.type == ChatMessage.Type.RECEIVE_TYPE) {
            holder.messageRight.visibility = View.GONE
            holder.messageLeft.text = message.content
        } else {
            holder.messageLeft.visibility = View.GONE
            holder.messageRight.text = message.content
        }
    }

}

data class ChatViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val messageLeft: TextView = view.findViewById(R.id.chat_left)
    val messageRight: TextView = view.findViewById(R.id.chat_right)
}