package org.javamaster.fragmentlearning.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * @author yudong
 * @date 2019/8/18
 */
data class User(
    var username: String,
    var picUrl: String,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
    var authorities: Set<SimpleGrantedAuthority>
) {
    constructor() : this("", "", false, false, false, true, mutableSetOf())
}
