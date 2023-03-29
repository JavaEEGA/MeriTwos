package se.iths.meritwos.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/api/**").hasAuthority(User.Role.ADMIN.getAuthority())
                .requestMatchers(HttpMethod.POST,"/api/companies/**").hasAnyAuthority(User.Role.COMPANY.getAuthority())
                .requestMatchers(HttpMethod.POST,"/api/students/**").hasAnyAuthority(User.Role.STUDENT.getAuthority())
                .requestMatchers(HttpMethod.POST,"/api/ads/**").hasAnyAuthority(User.Role.COMPANY.getAuthority())
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
                .requestMatchers("/ads").permitAll()
                .requestMatchers("/homepage").permitAll()
                .requestMatchers("/newad").hasAnyAuthority(User.Role.COMPANY.getAuthority(),User.Role.ADMIN.getAuthority())
                .requestMatchers("/newcompany").hasAnyAuthority(User.Role.COMPANY.getAuthority(),User.Role.ADMIN.getAuthority())
                .requestMatchers("/newstudent").hasAnyAuthority(User.Role.STUDENT.getAuthority(),User.Role.ADMIN.getAuthority())
                .requestMatchers("/style/**").permitAll()
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
