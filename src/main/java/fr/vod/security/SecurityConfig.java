package fr.vod.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private TokenAuthentificationFilter tokenAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // Disable CSRF for JWT stateless
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No session
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/public/**", "/public/*.mp4").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/mentor/**").hasRole("MENTOR")
						.requestMatchers("/mentoree/**").hasRole("MENTOREE")
						.requestMatchers("/api/**").authenticated()
						.anyRequest().authenticated()
				)
				.exceptionHandling(ex ->
						ex.authenticationEntryPoint((request, response, authException) -> {
							// Return 401 for unauthorized requests
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
						})
				)
				// Add your JWT token filter before UsernamePasswordAuthenticationFilter
				.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

		System.out.println("Je securise les accès");
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
