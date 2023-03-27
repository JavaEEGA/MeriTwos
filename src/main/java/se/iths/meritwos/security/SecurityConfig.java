package se.iths.meritwos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.iths.meritwos.user.User;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain restApiFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/api/**")
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/users/register").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .build();
        //TODO Add roles
    }

    @Bean
    public SecurityFilterChain defaultFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/ads").authenticated()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/ads")
                .and()
                .build();
        //TODO
        // Ska vi anvönda Oath2?
        // I så fall behöver vi en OidcService

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
