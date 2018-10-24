package com.jwt.service;

import com.jwt.entity.PortalUser;

/**
 * Created by Chong AiZhen on 18-10-22,下午4:37.
 */
public interface UserService {

    PortalUser getPortalUser(String username);

    String login(String username, String password);
}
