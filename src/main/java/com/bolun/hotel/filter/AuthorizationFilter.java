package com.bolun.hotel.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Set;

import static com.bolun.hotel.helper.UrlPath.*;

@WebFilter(ALL)
public class AuthorizationFilter implements Filter {

    private final Set<String> PUBLIC_PATHS = Set.of(REGISTRATION, LOGIN, APARTMENT);
    private final Set<String> LOGIN_PATHS = Set.of(USER_DETAIL, ORDER, USER_ORDERS);
    //private final Set<String>

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
