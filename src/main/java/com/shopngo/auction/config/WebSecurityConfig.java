package com.shopngo.auction.config;

import com.shopngo.auction.portal.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final List<String> NOT_SECURED_PATHS = Arrays.asList(
        "/",
        "/static/**",
        "/ws/**",
        "/login",
        "/*.{js,html}",
        "/public/**",
        "/manage/**",
        "/h2-console/**",
        //METRICS + "/**", ,
        "/v2/api-docs/**",
        "/swagger-resources/**",
        "/swagger-ui/index.html",
        "/webjars/springfox-swagger-ui/**"
    );
    @Autowired
    private JwtValidatorService jwtValidatorService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UnsuccessfulJwtAuthenticationFailureHandler unsuccessfulJwtAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                    .antMatchers(NOT_SECURED_PATHS.toArray(new String[NOT_SECURED_PATHS.size()])).permitAll()
            .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
                    //@formatter:on
    }

    private JwtFilter jwtFilter() throws Exception {
        var matcher = new SkipPathRequestMatcher(NOT_SECURED_PATHS, "/**");
        var filter = new JwtFilter(jwtValidatorService, unsuccessfulJwtAuthenticationFailureHandler, matcher);
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }
}
