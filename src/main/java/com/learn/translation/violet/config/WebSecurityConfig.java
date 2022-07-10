package com.learn.translation.violet.config;


import com.learn.translation.violet.security.MyDatabaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/api/v1/user").hasRole("ADMIN")
                .antMatchers("/api/v1/register", "/api/v1/login").permitAll()
                .antMatchers("/api/v1/*").authenticated()
                .and()
                .formLogin()
                .loginPage("/api/v1/login")
                .loginProcessingUrl("/api/v1/login")
                .defaultSuccessUrl("/api/v1/userinfo")
                .and()
                .logout()
                .logoutUrl("/api/v1/logout")
                .and()
                .csrf()
                .disable()
                .cors();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyDatabaseUserDetailsService();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

