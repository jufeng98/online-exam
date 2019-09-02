package org.javamaster.fragmentlearning.data.entity

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * @author yudong
 * @date 2019/8/30
 */
data class Book(
    @Column(unique = true, defaultValue = "") var author: String?,
    @Column(nullable = false) var price: Double?,
    var pages: Int?,
    var name: String?,
    @Column(ignore = true) var uselessField: String?
) : LitePalSupport() {
    constructor() : this(null, null, null, null, null)
}