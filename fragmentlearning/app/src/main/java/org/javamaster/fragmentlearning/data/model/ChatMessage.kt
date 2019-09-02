package org.javamaster.fragmentlearning.data.model

/**
 * @author yudong
 * @date 2019/8/25
 */
data class ChatMessage(val content: String, val type: Type) {
    enum class Type {
        SEND_TYPE,
        RECEIVE_TYPE
    }
}