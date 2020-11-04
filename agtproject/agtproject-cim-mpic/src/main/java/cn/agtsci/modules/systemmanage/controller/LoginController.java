package cn.agtsci.modules.systemmanage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author caiyt
 * @date 2019-11-06
 *
 * **/
@Api(tags = "系统管理")
@Controller
@RequestMapping("/systemmanage")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager myAuthenticationManager;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public String userLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            //使用SpringSecurity拦截登陆请求 进行认证和授权
            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            //使用redis session共享
            HttpSession session = request.getSession();
            // 这个非常重要，否则验证后将无法登陆
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }catch (Exception e){
            logger.error(e.getMessage());
            return "redirect:login-error?error=2";
        }
        return "redirect:index";
    }

}
