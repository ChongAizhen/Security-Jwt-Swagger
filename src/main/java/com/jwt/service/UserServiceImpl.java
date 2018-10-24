package com.jwt.service;

import com.jwt.entity.PortalUser;
import com.jwt.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Chong AiZhen on 18-10-22,下午4:37.
 */
@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public PortalUser getPortalUser(String username) {
        PortalUser user = new PortalUser();
        user.setUsername("test");
        user.setPassword("123456");
        user.setAuthority("ROLE_ADMIN");
        return user;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }
}
