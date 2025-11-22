package org.deepak.handlers;

import com.google.inject.Inject;
import graphql.ExecutionInput;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import graphql.GraphQL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GraphQLRequestHandler implements Handler {
    private final GraphQL graphQL;
    @Override
    public void handle(@NotNull Context context) throws Exception {
        log.debug("Handling GraphQL request from {}", context.ip());
        
        try {
            final Map<String, Object> body = context.bodyAsClass(Map.class);
            
            if (body == null || !body.containsKey("query")) {
                log.warn("Invalid request: Missing 'query' in request body from {}", context.ip());
                context.status(400).json(Map.of("error", "Missing 'query' in request body"));
                return;
            }
            
            String query = body.get("query").toString();
            log.info("Executing GraphQL query: {}", query);
            String operationName = (String) body.get("operationName");
            Map<String, Object> variables =
                    (Map<String, Object>) body.getOrDefault("variables", Map.of());
            ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(query)
                    .variables(variables)
                    .operationName(operationName)
                    .build();
            
            var result = graphQL.execute(executionInput);
            
            if (result.getErrors().isEmpty()) {
                log.debug("GraphQL query executed successfully");
            } else {
                log.warn("GraphQL query executed with {} errors: {}", result.getErrors().size(), result.getErrors());
            }
            
            context.json(result.toSpecification());
            
        } catch (Exception e) {
            log.error("Error processing GraphQL request: {}", e.getMessage(), e);
            context.status(500).json(Map.of("error", "Internal server error"));
        }
    }
}
