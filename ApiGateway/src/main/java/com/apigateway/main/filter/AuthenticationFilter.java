package com.apigateway.main.filter;

import com.apigateway.main.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JWTUtils jwtUtils;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtils.validateToken(authHeader);
                } catch (Exception e) {
                    System.out.println("invalid access!");
                    throw new RuntimeException("Un Authorized access to application");
                }
                Claims claims = jwtUtils.getClaims(authHeader);
                List<String> roles = (List<String>) claims.get("ROLES");

                if(!roles.contains("ROLE_USER")) {
                    throw new RuntimeException("Your role is not correct");
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
