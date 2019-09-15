package org.javamaster.fragmentlearning.data.model

/**
 * @author yudong
 * @date 2019/8/18
 */
data class User(
    var username: String = "",
    var picUrl: String = "",
    var email: String = "",
    var gender: String = "",
    var mobile: String = "",
    var remark: String = "",
    var accountNonExpired: Boolean = false,
    var accountNonLocked: Boolean = false,
    var credentialsNonExpired: Boolean = false,
    var enabled: Boolean = true,
    var authorities: Set<SimpleGrantedAuthority> = mutableSetOf()
)
