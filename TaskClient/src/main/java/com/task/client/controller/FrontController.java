package com.task.client.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    
    @GetMapping(
        value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/*/{y:[\\w\\-]+}","/error"  }
    )
    public String index() {
        return "/index.html";
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client) {
        System.out.println(client.getAccessToken().getTokenType().getValue());
        System.out.println(client.getAccessToken().getTokenValue());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
        
        // System.out.println("Id token => " + idToken);
        return ResponseEntity.ok().body(Map.of("message", "success"));
    }

}
