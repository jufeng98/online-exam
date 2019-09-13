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
    const val UPLOAD_EXCEPTIONS = "$BASE_URL$APP_CONTEXT/public/log/uploadExceptions"
    const val FIND_TOPICS_LIST = "$BASE_URL$APP_CONTEXT/core/topics/findTopicsList"
    const val FIND_SECTIONS_LIST = "$BASE_URL$APP_CONTEXT/core/sections/findSectionsList"
    const val FIND_KNOWLEDGES_LIST = "$BASE_URL$APP_CONTEXT/core/Knowledges/findKnowledgesList"
    const val FIND_KNOWLEDGES_QUESTION_NUM = "$BASE_URL$APP_CONTEXT/core/knowledgePoints/findKnowledgesQuestionNum"
}