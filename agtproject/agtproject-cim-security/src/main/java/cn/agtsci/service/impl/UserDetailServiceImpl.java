package cn.agtsci.service.impl;/**
 * Created by ShenYiDong on 2019/11/5
 */

import cn.agtsci.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import cn.agtsci.entity.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限判断
 *@author: shenyidong
 *@create: 2019-11-05
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        //生成环境是查询数据库获取username的角色用于后续权限判断（如：张三 admin)
        if("employee".equals(username)) {
            Employee employee = new Employee();
            employee.setUsername("employee");
            employee.setPassword("123456");
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_EMPLOYEE");
            grantedAuthorities.add(grantedAuthority);

            //加密后的密码
            String password = new BCryptPasswordEncoder().encode(employee.getPassword());
            System.out.println(password);

            //创建一个用户，用于判断权限，请注意此用户名和方法参数中的username一致；BCryptPasswordEncoder是用来演示加密使用。
            return new User(employee.getUsername(), new BCryptPasswordEncoder().encode(employee.getPassword()), grantedAuthorities);
        }

        if("admin".equals(username)){
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("123456");
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            grantedAuthorities.add(grantedAuthority);
            return new User(admin.getUsername(), new BCryptPasswordEncoder().encode(admin.getPassword()), grantedAuthorities);
        }
        else {
            return null;
        }


    }
}