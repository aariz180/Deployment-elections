package com.election.electionsbackend.config;

import com.election.electionsbackend.model.user.Role;
import com.election.electionsbackend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configures CORS (Cross-Origin Resource Sharing) to allow requests from specific origins
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors ->
                        // Lambda expression defining the CORS configuration logic
                        cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:3000");  // Allow front-end origin
                    config.addAllowedHeader("*");  // Allow all headers
                    config.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
                    return config;
                }))

                // Configures authorization rules for HTTP requests
                .authorizeHttpRequests(request ->
                        // Lambda expression defining authorization rules for different endpoints
                        request

                            // Permits all requests to the following endpoints without authentication
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers("/api/v1/auth/signin").permitAll()
                            .requestMatchers("/api/v1/auth/refresh").permitAll()  // Allow access to refresh token
                            // Requires ADMIN authority for accessing admin-related endpoints
                            .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                            // Requires USER authority for accessing user-related endpoints
                            .requestMatchers("/api/v1/users").hasAnyAuthority(Role.USER.name())
                            // Permits all requests to the following endpoints without authentication
                            .requestMatchers("/parties/*").permitAll()
                            .requestMatchers("/parties/all").permitAll()
                            .requestMatchers("/parties/name/*").permitAll()
                            .requestMatchers("/votes/*").permitAll()
                            .requestMatchers("/candidates/*").permitAll()
                            .requestMatchers("/forums/**").permitAll()
                            .requestMatchers("/messages/**").permitAll()
                            .requestMatchers("/tags/*").permitAll()
                            .requestMatchers("/api/v1/elections/**").permitAll()
                            // Requires authentication for any other requests
                            .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // No session will be created
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userService.getUserDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
