package com.sevenXnetworks.treasure.web.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 全局跨域配置
 * 1.解决基于Spring Security的跨域问题
 * 2.其余Spring MVC跨域问题详见{@link}
 * 3.另外的跨域问题在Nginx中配置
 * </p>
 */
@Component
@Order(1)
public class WebMvcCorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        crossDomain(response);
        filterChain.doFilter(request, response);
    }

    // 跨域配置
    private void crossDomain(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
//        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
    }
    
}