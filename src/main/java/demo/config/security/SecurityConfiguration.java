package demo.config.security;

import demo.dao.SysPermissionMapper;
import demo.model.SysPermission;
import demo.service.MyUserDetailsService;
import demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

import java.util.List;

/**
 * @author admin
 * 2022/7/13 16:07
 **/
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private SysPermissionMapper permissionMapper;

//    private static PasswordEncoder encoder;

//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/test/*").permitAll()
//                .antMatchers("/swagger-ui").permitAll()
//                .antMatchers("/api/*").authenticated()
//                .and()
//                .requestCache()
//                .requestCache(new NullRequestCache())
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
//                .and().cors().disable();
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailsService).passwordEncoder(password());
//    }

//    @Bean
//    public PasswordEncoder password() {
//        if (encoder == null) {
//            encoder = new BCryptPasswordEncoder();
//        }
//        return encoder;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().formLogin();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
        List<SysPermission> permissionList = permissionMapper.selectAll();
        permissionList.forEach(p -> {
            urlRegistry.antMatchers(p.getUrl()).hasAnyAuthority(p.getPermtag());
        });
        // 可以允许login 不被拦截
        urlRegistry.antMatchers("/login").permitAll()
                .antMatchers("/test/**").permitAll()
               // 设置自定义登录页面
                .antMatchers("/**").fullyAuthenticated().and().formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String rawPass = MD5Util.encode((String) rawPassword);
                return rawPass.equals(encodedPassword);
            }
        };
    }
}
