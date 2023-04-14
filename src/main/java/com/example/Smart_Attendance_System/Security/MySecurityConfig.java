package com.example.Smart_Attendance_System.Security;

import com.example.Smart_Attendance_System.Dao.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class MySecurityConfig
{
    @Autowired
    ConfigRepo configRepo;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/assets/**","/login","/student/**","/teacher/**","/passkey/","/scancard/","/mark/").permitAll()
                .requestMatchers("/admindashboard/**", "/student/**").hasRole("ADMIN")
                .requestMatchers("/","").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
//                    .loginProcessingUrl("/admindashboard/")
                .defaultSuccessUrl("/admindashboard/", true)
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .rememberMe().rememberMeParameter("remember_me").key("mySecreteKey").tokenValiditySeconds(60 * 60 * 60 * 24 * 7);

        return http.build();
    }

    @Bean
    protected UserDetailsService adminDetailsService() {
        List<UserDetails> listUser = new ArrayList<>();
        listUser.add(
                User.builder()
                        .username(configRepo.getVal("username"))
                        .password(configRepo.getVal("password"))
                        .roles("ADMIN")
                        .build()
        );
        return new InMemoryUserDetailsManager(listUser);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
