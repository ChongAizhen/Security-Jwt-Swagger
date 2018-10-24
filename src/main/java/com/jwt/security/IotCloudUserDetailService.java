package com.jwt.security;

import com.jwt.entity.PortalUser;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class IotCloudUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 根据用户名获取用户 - 用户的角色、权限等信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails userDetails = null;
//        try {
//
//            com.jwt.entity.PortalUser user = userService.getPortalUser(username);
//            Collection<GrantedAuthority> authList = getAuthorities(user.getAuthority());
//            userDetails = new User(username, user.getPassword(), true, true, true, true, authList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return userDetails;

        PortalUser user = userService.getPortalUser(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }

    }

    private Collection<GrantedAuthority> getAuthorities(String auth) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority(auth));
        return authList;
    }
}