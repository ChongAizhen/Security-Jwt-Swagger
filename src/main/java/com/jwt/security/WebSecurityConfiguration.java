package com.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, EntryPointUnauthorizedHandler entryPointUnauthorizedHandler, RestAccessDeniedHandler restAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.entryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()

                //swagger-ui
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()

                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/noauth/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                //基于token，所以不需要session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //禁用缓存
                .and().headers().cacheControl();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);

    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService);
    }

}
