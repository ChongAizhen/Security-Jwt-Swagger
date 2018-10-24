package com.jwt.security;

/**
 * Created by Chong AiZhen on 18-10-22,下午5:02.
 */

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定403返回值
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(403);
    }

}
