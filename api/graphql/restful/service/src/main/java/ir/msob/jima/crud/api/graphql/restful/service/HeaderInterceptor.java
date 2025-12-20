package ir.msob.jima.crud.api.graphql.restful.service;

import org.springframework.http.HttpHeaders;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The {@code HeaderInterceptor} class is a Spring component that implements the
 * {@code WebGraphQlInterceptor} interface. It is responsible for intercepting GraphQL requests
 * and extracting the "Authorization" header from the request. The extracted header is then added
 * to the GraphQL context, allowing it to be accessed during the execution of GraphQL queries.
 * <p>
 * This interceptor is specifically designed to handle the "Authorization" header and include it
 * in the GraphQL context for further processing.
 *
 * @see WebGraphQlInterceptor
 * @see WebGraphQlRequest
 * @see WebGraphQlResponse
 * @see HttpHeaders
 */
@Component
public class HeaderInterceptor implements WebGraphQlInterceptor {

    /**
     * Intercepts the GraphQL request, extracts the "Authorization" header, and adds it to the
     * GraphQL context before passing the request to the next interceptor in the chain.
     *
     * @param request The GraphQL request.
     * @param chain   The interceptor chain.
     * @return A Mono representing the GraphQL response after processing the interceptor chain.
     */
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        List<String> authorizationHeaders = request.getHeaders().getOrDefault(HttpHeaders.AUTHORIZATION, new ArrayList<>());
        if (!authorizationHeaders.isEmpty()) {
            String firstAuthorizationHeader = authorizationHeaders.getFirst();
            Map<?, Object> context = Collections.singletonMap(HttpHeaders.AUTHORIZATION, firstAuthorizationHeader);
            request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(context).build());
        }
        return chain.next(request);
    }
}
