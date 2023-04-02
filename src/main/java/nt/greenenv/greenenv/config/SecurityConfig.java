package nt.greenenv.greenenv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // login 없이 접근 허용 하는 url
                .antMatchers("/**", "/shop/product/**").permitAll()
                .antMatchers("/shop/**").hasAnyAuthority("seller")
                .antMatchers("/shop/order/**").authenticated()
                // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
                .antMatchers("/admin").hasAnyAuthority("admin");
        http.formLogin()
                .loginPage("/member/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .defaultSuccessUrl("/")
                .failureHandler((request, response, exception) -> {

                    String errorMessage;
                    if (exception instanceof BadCredentialsException) {
                        errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
                    } else if (exception instanceof InternalAuthenticationServiceException) {
                        errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
                    } else if (exception instanceof UsernameNotFoundException) {
                        errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
                    } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                        errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
                    } else {
                        errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
                    }
//                        new SimpleUrlAuthenticationFailureHandler().setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
                    request.setAttribute("error", errorMessage);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/member/login");
                    dispatcher.forward(request, response);
                });
        http.csrf().disable();
        http.logout()
                .logoutUrl("/member/logout")
                .logoutSuccessUrl("/");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}