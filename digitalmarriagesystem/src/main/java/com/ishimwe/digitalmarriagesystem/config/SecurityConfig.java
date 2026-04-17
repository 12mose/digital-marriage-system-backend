package com.ishimwe.digitalmarriagesystem.config;

import com.ishimwe.digitalmarriagesystem.security.CustomUserDetailsService;
import com.ishimwe.digitalmarriagesystem.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final UserActivityFilter userActivityFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService userDetailsService, UserActivityFilter userActivityFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.userActivityFilter = userActivityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(userActivityFilter, JwtAuthenticationFilter.class)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**", "/api/public/**", "/", "/dashboard", "/index.html", "/dashboard.html", "/verify.html", "/static/**").permitAll()
                .requestMatchers("/api/users/search").authenticated()
                
                // User Management: Officers can view, but only Admins can create/promote/delete
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                
                .requestMatchers("/api/auditlogs/**", "/api/reports/**").hasRole("ADMIN")
                
                // Marriages: Admin/Officer can manage, Citizen can only GET (service filtered)
                // Delete restricted to Admin
                .requestMatchers(HttpMethod.DELETE, "/api/marriages/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/marriages/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers("/api/marriages/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                
                // Applications: Admin/Officer can manage, Citizen can apply/approve-partner
                // Delete restricted to Admin
                .requestMatchers(HttpMethod.DELETE, "/api/applications/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/applications/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers(HttpMethod.POST, "/api/applications/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers(HttpMethod.PUT, "/api/applications/*/partner-approve").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers(HttpMethod.PUT, "/api/applications/*/status").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                .requestMatchers("/api/applications/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                
                // Certificates: Admin/Officer manage, Citizen GET own
                .requestMatchers(HttpMethod.DELETE, "/api/certificates/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/certificates/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers("/api/certificates/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                
                // Documents: Admin/Officer manage, Citizen GET/POST
                .requestMatchers(HttpMethod.DELETE, "/api/documents/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/documents/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers(HttpMethod.POST, "/api/documents/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER", "CITIZEN")
                .requestMatchers("/api/documents/**").hasAnyRole("ADMIN", "MARRIAGE_OFFICER")
                
                .requestMatchers("/api/appointments/**").authenticated()
                .requestMatchers("/api/messages/**").authenticated()
                .anyRequest().authenticated()
            );
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
