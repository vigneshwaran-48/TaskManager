// package com.task.resource.config;

// import java.util.Arrays;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtDecoders;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.google.common.net.HttpHeaders;

// @EnableWebSecurity
// @Configuration
// public class SecurityConfig {
    
//     @Value("${authserver.baseurl}")
// 	private String authServerUrl;

//     private static final Long MAX_AGE = 3600L;
	
// 	@Bean
// 	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// 		return http
// 				.sessionManagement()
// 					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
// 				.and()
// 				.csrf()
// 				.disable()
// 				.authorizeHttpRequests(request -> {
// 					request
// 					.requestMatchers("/test").permitAll()
// 					.requestMatchers(HttpMethod.POST, "/api/user").permitAll()
// 					.requestMatchers(HttpMethod.OPTIONS, "/api/user")
// 					.permitAll()
// 							.requestMatchers(HttpMethod.GET, "/api/user/{userId}/profile-image")
// 							.permitAll()
// 					.anyRequest().authenticated();
// 				})
// 				.oauth2ResourceServer(oauth2 -> oauth2.jwt())
// 				.build();
// 	}
// 	@Bean
// 	JwtDecoder jwtDecoder() {
// 	    return JwtDecoders.fromIssuerLocation(authServerUrl);
// 	}

//     @Bean
// 	CorsConfigurationSource corsConfigurationSource() {
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		CorsConfiguration config = new CorsConfiguration();
// 		config.setAllowCredentials(true);
// 		config.setAllowedOrigins(Arrays.asList("*"));
// 		config.setAllowedHeaders(Arrays.asList(
// 					HttpHeaders.AUTHORIZATION,
// 					HttpHeaders.CONTENT_TYPE,
// 					HttpHeaders.ACCEPT
// 				));
// 		config.setAllowedMethods(Arrays.asList(
// 					HttpMethod.GET.name(),
// 					HttpMethod.POST.name(),
// 					HttpMethod.PUT.name(),
// 					HttpMethod.DELETE.name(),
// 					HttpMethod.OPTIONS.name()
// 				));
// 		config.setMaxAge(MAX_AGE);
// 		source.registerCorsConfiguration("/**", config);	
		
// 		return source;
// 	}
// }
