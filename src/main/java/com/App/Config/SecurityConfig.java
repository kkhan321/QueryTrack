package com.App.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserCustomerServiceDetails();
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(getUserDetailService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(registry -> {
                       registry.requestMatchers("/*").permitAll(); // Home page accessible to all
                       registry.requestMatchers("/admin/**").hasRole("ADMIN");
                       registry.requestMatchers("/user/**").hasRole("USER");
                       registry.requestMatchers("/emp/**").hasRole("EMP");
                       registry.anyRequest().authenticated();
                   })
                   .formLogin(httpSecurityFormLoginConfigurer -> {
                       httpSecurityFormLoginConfigurer.loginPage("/signin")
                                                       .loginProcessingUrl("/userLogin")
                                                       .usernameParameter("email")
                                                       .successHandler(new CustomAuthSuccessHandler())
                                                       .permitAll();
                   })
                   .build();
    }

}
