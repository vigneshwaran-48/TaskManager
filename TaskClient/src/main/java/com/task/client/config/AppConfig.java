package com.task.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client
        .ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.task.library.exception.AppException;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfig {

        @Value("${app.resource.server.baseurl}")
        private String resourceServerBaseURL;

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
        	return new BCryptPasswordEncoder();
        }

    @Bean
    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        
        int size = 16 * 1024 * 1024 * 2;
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
        
        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
                        .baseUrl(resourceServerBaseURL)
        		// .filter(oauth2Client)
                        .filter((request, next) -> {
                                OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                                String idToken = ((DefaultOidcUser) oauth2Token.getPrincipal()).getIdToken().getTokenValue();
                                ClientRequest clientRequest = ClientRequest.from(request)
                                        .header("Authorization", "Bearer " + idToken)
                                        .build();
                                return next.exchange(clientRequest);
                        })
                        .filter(errorHandler())
        		.exchangeStrategies(strategies)
                .build();
    }

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    static ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
                if(response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError()) {
                        return response.bodyToMono(String.class)
                                        .flatMap(errorBody -> {
                                                System.out.println(errorBody);
                                                JSONObject errorResp = (JSONObject) JSONValue.parse(errorBody);
                                                
                                                return Mono.error(
                                                        new AppException(errorResp.getAsString("error"), 
                                                                response.statusCode().value()));
                                        });
                }
                else {
                        return Mono.just(response);
                }
        });
    }
}
