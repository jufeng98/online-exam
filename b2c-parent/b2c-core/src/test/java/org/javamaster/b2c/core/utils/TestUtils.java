package org.javamaster.b2c.core.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

/**
 * @author yudong
 * @date 2021/3/24
 */
public class TestUtils {

    public static UserDetails mockUserDetails() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return new User("admin", "admin", Collections.singleton(authority));
    }

}
