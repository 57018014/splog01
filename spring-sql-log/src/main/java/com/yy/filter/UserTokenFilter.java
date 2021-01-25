package com.yy.filter;


import com.github.phantomthief.scope.Scope;
import com.yy.bean.User;
import com.yy.scope.CurrentScope;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录
 * order 2
 */
public class UserTokenFilter extends OncePerRequestFilter {

    private static final String DEFAULT_TOKEN_HEAD_KEY = "Authorization"; // token放得位置

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        Scope.beginScope();
        try {
            String token = request.getHeader(DEFAULT_TOKEN_HEAD_KEY);
            long nowTime = System.currentTimeMillis();
            testUser();
            checkTokenAndFullCurrentScope(request, response, filterChain, token, nowTime);
        } finally {
            Scope.endScope();
        }
    }

    private void testUser() {
        User user = new User();
        user.setId(1L);
        user.setName("hello");
        CurrentScope.setLoginUserId(1L);

    }

    private void checkTokenAndFullCurrentScope(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String token, long nowTime) throws IOException, ServletException {
        CurrentScope.setLoginUserToken(token);
        filterChain.doFilter(request, response);
    }
/*
    private UserTokenDO checkTokenAndGetUserInfo(String token, long nowTime, HttpServletRequest request) {

        String userRedis = USER_TOKEN.getWarpper().get(USER_TOKEN.getProfix() + token);

        if (com.pbkj.cloud.framework.utils.StringUtils.isJedisEmpty(userRedis)) {

            String requestURI = request.getRequestURI();
            if (requestURI.contains("/bpserver/live/app/init")) {
                throw new ServiceException(ERROR_GONE, "请登陆");
            }

            throw new ServiceException(ERROR_IDENTITY, "请登陆");
        }
        UserTokenDO userToken = JSONObject.parseObject(userRedis, UserTokenDO.class);
        if (userToken.isExpired(nowTime)) {
            String requestURI = request.getRequestURI();
            if (requestURI.contains("/bpserver/live/app/init")) {
                throw new ServiceException(ERROR_GONE, "请登陆");
            }
            throw new ServiceException(ERROR_PERMISSIONS, "登陆过期，请重新登陆");
        }
        return userToken;
    }*/
}
