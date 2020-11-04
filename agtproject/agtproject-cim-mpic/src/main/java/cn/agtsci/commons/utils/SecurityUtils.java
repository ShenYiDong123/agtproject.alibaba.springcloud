package cn.agtsci.commons.utils;

import cn.agtsci.commons.constants.AuthoritiesConstants;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author caiyt
 * @date 2019-11-05
 *
 * **/
public class SecurityUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    /**
     * 获取当前权限信息
     *
     *
     * **/
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取所有授权
     *
     *
     * **/
    public static Collection<? extends GrantedAuthority> getAllPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    /**
     * 获取当前登录帐号
     *
     * @return 返回当前登录帐号
     */
    public static String getCurrentUserLogin() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String userName = null;
            if (authentication != null) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    userName = springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    userName = (String) authentication.getPrincipal();
                }
            }
            if(StringUtils.isNotBlank(userName)){
                return userName;
            }
        }catch (Exception e){
            LOGGER.error("获取当前登录用户失败");
        }
        return "anonymousUser";
    }

    /**
     * 验证用户是否有权限
     *
     * @return true：有权限，false：没有权限
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * 验证当前用户是否有权限
     *
     *
     * @param authority 待校验权限
     * @return true：当前用户拥有该权限，false：当前用户没有该权限
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }

    /**
     * 获取当前登录帐号详细信息
     *
     * @return 返回账号详细信息
     *
     * ***/
    public static UserDetails getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                return (UserDetails) authentication.getPrincipal();
            }
        }
        return null;
    }

    /**
     * 登出，清空上下文信息
     *
     * */
    public static void logout(){
        SecurityContextHolder.clearContext();
    }
}
