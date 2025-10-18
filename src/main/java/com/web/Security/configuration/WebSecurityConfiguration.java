package com.web.Security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/trainer/**").hasAnyRole("TRAINER", "ADMIN")
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "TRAINER", "ADMIN")
                )
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(12);

    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("harshil")
//                .password(passwordEncoder().encode("harshil@123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails trainer = User.withUsername("prakruti")
//                .password(passwordEncoder().encode("prakruti@123"))
//                .roles("TRAINER")
//                .build();
//
//        UserDetails user = User.withUsername("tirth")
//                .password(passwordEncoder().encode("tirth@123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, trainer, user);
//    }

}
