package com.jwt.security;

import com.jwt.entity.PortalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Chong AiZhen on 18-10-22,下午3:03.
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(PortalUser user) {
        return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getAuthority())
        );
    }

    private static Collection<GrantedAuthority> getAuthorities(String auth) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority(auth));
        return authList;
    }
}
