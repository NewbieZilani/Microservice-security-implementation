package com.apigateway.main.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints= List.of(
            "/security/registration",
            "/login",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured = request -> {
        // Get the path of the incoming request
        String requestPath = request.getURI().getPath();

        // Check if the request path matches any open API endpoint
        boolean isUnsecured = openApiEndpoints.stream().anyMatch(requestPath::contains);

        // If it's unsecured, return false (i.e., not secured)
        // If it's not in openApiEndpoints, return true (i.e., secured)
        return !isUnsecured;
    };

}
