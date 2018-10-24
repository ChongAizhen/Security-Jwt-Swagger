package com.jwt.security;

/**
 * Created by Chong AiZhen on 18-10-22,下午5:01.
 */

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 自定401返回值
 * 自定义了身份验证失败的返回值
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(401);
    }

}
