package com.practise.Smart_Arena.configuration;

import com.practise.Smart_Arena.service.auth.LoginService;
import com.practise.Smart_Arena.service.jwtService.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    final private String[] WHITE_LIST = {"/api/login/numberValidate", "/api/login/otpCheck", "/api/owner/registerOwner", "/api/player/registerPlayer"};

    final private String STADIUM_API = "/api/stadium";
    final private String QULAYLIKLAR_API = "/api/qulayliklar";
    final private String POLYA_API = "/api/polya";
    final private String COMMENT_API = "/api/comment";
    final private String TEAM_API = "/api/team";
    final private String STATUS_API = "/api/status";

    @Autowired
    private LoginService logSer;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestConfigurer -> {
                    requestConfigurer
                            .requestMatchers(WHITE_LIST).permitAll()
                            .requestMatchers(STADIUM_API + "/createStadium/{ownerId}").hasRole("OWNER")
                            .requestMatchers(QULAYLIKLAR_API + "/createQulayliklar/{stadiumId}").hasRole("OWNER")
                            .requestMatchers(POLYA_API + "/createPolya/{stadiumId}").hasRole("OWNER")
                            .requestMatchers(TEAM_API + "/createTeam").hasRole("PLAYER")
                            .requestMatchers(STATUS_API + "/createStatus").hasRole("PLAYER")
                            .requestMatchers(COMMENT_API + "/createComment").hasRole("PLAYER")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(logSer)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
