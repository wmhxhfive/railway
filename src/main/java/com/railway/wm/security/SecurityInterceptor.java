package com.railway.wm.security;

import com.railway.wm.domain.UserInfoDao;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();

//            判断是否已有该用户登录的session
/*        if(session.getAttribute("cur_user") != null){
            return true;
        }

        Object obj = request.getSession().getAttribute("cur_user");
        if (obj == null || !(obj instanceof UserInfoDao)) {
            //response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }*/

        return true;
    }


}
