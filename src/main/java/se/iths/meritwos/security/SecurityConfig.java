package se.iths.meritwos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain restApiFilter(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                // Impementera
//                //TODO
//
//                .build();
//    }

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

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
