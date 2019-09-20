package org.javamaster.fragmentlearning.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.lzt.tiptextviews.view.TipLayout
import de.hdodenhof.circleimageview.CircleImageView
import org.javamaster.fragmentlearning.R
import org.javamaster.fragmentlearning.asyncTask.MarkMessagesTask
import org.javamaster.fragmentlearning.data.entity.Messages
import org.javamaster.fragmentlearning.enums.ReadEnum
import org.javamaster.fragmentlearning.listener.OperationListener
import org.javamaster.fragmentlearning.service.MessagesService
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author yudong
 * @date 2019/9/20
 */
@Suppress("DEPRECATION")
class MessagesAdapter(
    private var messagesList: List<Messages>, val messagesService: MessagesService
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    private var tipLayoutSet: MutableSet<TipLayout> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_messages_view_page, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messages = messagesList[position]
        holder.apply {
            if (messages.alreadyRead == ReadEnum.UNREAD.code.toByte()) {
                messagesCorner.setTipCout(8)
            } else {
                messagesCorner.setTipCout(0)
            }
            tipLayoutSet.add(messagesCorner)
            messagesContent.text = Html.fromHtml(messages.content)
            messagesDate.text =
                SimpleDateFormat(mContext.getString(R.string.chinese_date_pattern), Locale.SIMPLIFIED_CHINESE).format(
                    messages.createTime
                )
            messagesConstraintLayout.setOnClickListener {
                MarkMessagesTask(messagesService, object : OperationListener<Int> {
                    override fun success(t: Int) {
                        messages.alreadyRead = ReadEnum.READ.code.toByte()
                        messagesCorner.setTipCout(0)
                        Toast.makeText(mContext, mContext.getString(R.string.app_completing), Toast.LENGTH_SHORT)
                            .show()
                    }
                }).execute(messages.id)
            }
        }
    }

    fun markAllAsRead() {
        messagesList.forEach {
            it.alreadyRead = ReadEnum.READ.code.toByte()
        }
        tipLayoutSet.forEach {
            it.setTipCout(0)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messagesConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.messages_constraint_layout)
        val messagesCorner: TipLayout = itemView.findViewById(R.id.messages_corner)
        val messagesCoverImg: CircleImageView = itemView.findViewById(R.id.messages_cover_img)
        val messagesContent: TextView = itemView.findViewById(R.id.messages_content)
        val messagesDate: TextView = itemView.findViewById(R.id.messages_date)
    }
}