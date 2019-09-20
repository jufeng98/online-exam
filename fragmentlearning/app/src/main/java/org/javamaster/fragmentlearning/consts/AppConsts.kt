package org.javamaster.fragmentlearning.consts

import org.javamaster.fragmentlearning.BuildConfig
import org.javamaster.fragmentlearning.R

/**
 * @author yudong
 * @date 2019/8/18
 */
object AppConsts {
    const val LOGIN_ERROR_CODE = -1
    const val ERROR_CODE = 1
    const val ERROR_MSG = R.string.network_error

    const val BASE_URL = BuildConfig.BASE_URL
    const val APP_CONTEXT = "/onlineExam"
    const val LOGIN_URL = "$BASE_URL$APP_CONTEXT/core/login"
    const val LOGOUT_URL = "$BASE_URL$APP_CONTEXT/core/logout"
    const val SIGN_UP_URL = "$BASE_URL$APP_CONTEXT/admin/users/createUsers"
    const val EDIT_URL = "$BASE_URL$APP_CONTEXT/admin/users/editUsers"
    const val UPDATE_USERS_PASSWORD = "$BASE_URL$APP_CONTEXT/admin/users/updateUsersPassword"
    const val UPLOAD_EXCEPTIONS = "$BASE_URL$APP_CONTEXT/public/log/uploadExceptions"
    const val FIND_TOPICS_LIST = "$BASE_URL$APP_CONTEXT/core/topics/findTopicsList"
    const val FIND_SECTIONS_LIST = "$BASE_URL$APP_CONTEXT/core/sections/findSectionsList"
    const val FIND_KNOWLEDGES_LIST = "$BASE_URL$APP_CONTEXT/core/knowledges/findKnowledgesList"
    const val FIND_KNOWLEDGES_QUESTION_NUM = "$BASE_URL$APP_CONTEXT/core/knowledgePoints/findKnowledgesQuestionNum"
    const val FIND_KNOWLEDGE_POINTS_LIST = "$BASE_URL$APP_CONTEXT/core/knowledgePoints/findKnowledgePointsList"
    const val FIND_QUESTIONS_LIST = "$BASE_URL$APP_CONTEXT/core/questions/findQuestionsList"
    const val FIND_OPTIONS_LIST = "$BASE_URL$APP_CONTEXT/core/options/findAssociateOptions"
    const val UPLOAD_FILE = "$BASE_URL$APP_CONTEXT/core/files/uploadFile"
    const val FIND_EXAMS_LIST = "$BASE_URL$APP_CONTEXT/core/exams/findExamsList"
    const val FIND_QUESTIONS_BY_EXAMS_CODE = "$BASE_URL$APP_CONTEXT/core/questions/findQuestionsByExamsCode"
    const val SAVE_LEARNS = "$BASE_URL$APP_CONTEXT/core/learns/saveLearns"
    const val FIND_TOPICS_PROGRESS = "$BASE_URL$APP_CONTEXT/core/learns/findTopicsProgress"
    const val FIND_SECTIONS_PROGRESS = "$BASE_URL$APP_CONTEXT/core/learns/findSectionsProgress"
    const val SUBMIT_ANSWERS = "$BASE_URL$APP_CONTEXT/core/examsRecord/submitAnswers"
    const val HAS_UNREAD_MESSAGES = "$BASE_URL$APP_CONTEXT/core/messages/hasUnreadMessages"
    const val FIND_MESSAGES_LIST = "$BASE_URL$APP_CONTEXT/core/messages/findMessagesList"
    const val MARK_MESSAGES = "$BASE_URL$APP_CONTEXT/core/messages/markMessages"
}