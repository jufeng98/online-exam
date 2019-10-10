package org.javamaster.b2c.core.model.vo

import org.javamaster.b2c.core.model.Page

data class FindDiscussionsListReqVo(var question: String = "", var sort: Int = 0, var page: Page = Page(1, 1))
