// package com.task.client.config;

// import java.util.Arrays;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.google.common.net.HttpHeaders;
// import com.task.client.controller.CsrfFilter;

// import io.netty.handler.codec.http.HttpMethod;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
    
//     private static final Long MAX_AGE = 3600L;

//     @Bean
// 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// 		return http
// 				.cors()
// 					.configurationSource(corsConfigurationSource())
// 				.and()
// 				.csrf((csrf) -> csrf
// 		                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
// 		                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
// 		            )
// 				.addFilterAfter(new CsrfFilter(), BasicAuthenticationFilter.class)
// 				.authorizeHttpRequests(request -> {
// 					try {
// 						request
// 							.requestMatchers("/static/**",
// 			                    "/*.ico", "/*.json", "/*.png", "/ping",
// 									"/logout-success")
// 							.permitAll()
// 							.anyRequest()
// 							.authenticated()
// 							.and()
// 							.oauth2Login()							
// 							.permitAll()
// 								.and()
// 								.logout()
// 								.clearAuthentication(true)
// 								.deleteCookies()
// 								.invalidateHttpSession(true)
// 								.logoutSuccessUrl("/logout-success");
// 					} catch (Exception e) {
// 						e.printStackTrace();
// 					}
// 			   })				
// 			.build();
// 	}

//     @Bean
// 	CorsConfigurationSource corsConfigurationSource() {
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		CorsConfiguration config = new CorsConfiguration();
// 		config.setAllowCredentials(true);
// 		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:9090"));
// 		config.setAllowedHeaders(Arrays.asList(
// 					HttpHeaders.AUTHORIZATION,
// 					HttpHeaders.CONTENT_TYPE,
// 					HttpHeaders.ACCEPT,
// 					"X-XSRF-TOKEN"
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
