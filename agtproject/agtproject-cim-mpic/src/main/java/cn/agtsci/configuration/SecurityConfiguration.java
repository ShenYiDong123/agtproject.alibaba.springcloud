package cn.agtsci.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author caiyt
 * @date 2019-11-05
 *
 * **/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     *
     * 自定义权限校验
     *
     * **/
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //passwoldEncoder是对密码的加密处理，如果user中密码没有加密，则可以不加此方法。注意加密请使用security自带的加密方式。
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
        //禁用了 csrf 功能
//        http.csrf().disable()
            //限定签名成功的请求
//            .authorizeRequests()
            //对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
//            .antMatchers("/post-basic-information/**","/role-basic-information/**").hasRole("ADMIN")
//            .antMatchers("/login").permitAll()
//            .antMatchers("/post-basic-information/**").hasAnyRole("ADMIN,OTHERS")
//            .antMatchers("/oauth/**").permitAll()
            //其他没有限定的请求，允许访问
//            .anyRequest().authenticated()
            //对于没有配置权限的其他请求允许匿名访问
//            .and().anonymous()
            //使用 spring security 默认登录页面
//            .and().formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login?error=1")
            //权限拒绝页面
//            .and().exceptionHandling().accessDeniedPage("/403")
            //启用http 基础验证
//            .and().httpBasic();
//        http.logout().logoutSuccessUrl("/login");
    }

    /**
     * AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
