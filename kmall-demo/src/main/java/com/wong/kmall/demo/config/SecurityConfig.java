package com.wong.kmall.demo.config;

import com.wong.kmall.demo.component.JwtAuthenticationTokenFilter;
import com.wong.kmall.demo.component.RestAuthenticationEntryPoint;
import com.wong.kmall.demo.component.RestfulAccessDeniedHandler;
import com.wong.kmall.demo.dto.AdminUserDetails;
import com.wong.kmall.demo.service.UmsAdminService;
import com.wong.kmall.entity.UmsAdmin;
import com.wong.kmall.entity.UmsPermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/2 21:19
 * @description
 */
@Configuration
@EnableWebSecurity
// 开启Spring Security注解功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UmsAdminService umsAdminService;
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;// 当用户没有访问权限时的处理器，用于返回JSON格式的处理结果
    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;// 当未登录或token失效时，返回JSON格式的结果

    /**
     * 用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()// 由于使用的是JWT，我们这里不需要csrf
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于token，所以不需要session
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,// 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/webjars/springfox-swagger-ui/**"
                )
                .permitAll()
                .antMatchers("/admin/login", "/admin/register")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)// 跨域请求会先进行一次options请求
                .permitAll()
                .anyRequest()// 除上面外的所有请求需要鉴权认证
                .authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 用于配置UserDetailsService及PasswordEncoder
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录
     *
     * @return
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> {
            UmsAdmin admin = umsAdminService.getAdminByUsername(username);
            if (admin != null) {
                List<UmsPermission> permissionList = umsAdminService.getPermissionList(admin.getId());
                // SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现
                return new AdminUserDetails(admin, permissionList);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    /**
     * SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
