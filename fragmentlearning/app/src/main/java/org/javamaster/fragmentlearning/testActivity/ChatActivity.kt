package org.javamaster.fragmentlearning.testActivity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_chat.*
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.data.model.ChatMessage
import org.javamaster.fragmentlearning.testAdapter.ChatAdapter
import org.javamaster.fragmentlearning.ui.activities.BaseAppActivity

class ChatActivity : BaseAppActivity() {

    private var messageList = mutableListOf<ChatMessage>()
    var adapter = ChatAdapter(messageList)

    override fun initContentView(): Int? {
        return R.layout.activity_chat
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = chat_content_recycler_view
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        initMessage()
        recyclerView.adapter = adapter
    }

    @OnClick(R.id.chat_send)
    fun sendMessage() {
        val content = chat_input.text.toString()
        if (content == "") {
            return
        }
        val msg = ChatMessage(content, ChatMessage.Type.SEND_TYPE)
        messageList.add(msg)
        adapter.notifyItemInserted(messageList.size - 1)
        chat_content_recycler_view.scrollToPosition(messageList.size - 1)
        chat_input.setText("")
    }

    private fun initMessage() {
        val msg1 = ChatMessage("How are you?", ChatMessage.Type.SEND_TYPE)
        messageList.add(msg1)
        val msg2 = ChatMessage("I'm fire. And you?", ChatMessage.Type.RECEIVE_TYPE)
        messageList.add(msg2)
        val msg3 = ChatMessage("I'm fire too.", ChatMessage.Type.SEND_TYPE)
        messageList.add(msg3)
        val msg4 = ChatMessage("Good.", ChatMessage.Type.RECEIVE_TYPE)
        messageList.add(msg4)
    }
}
