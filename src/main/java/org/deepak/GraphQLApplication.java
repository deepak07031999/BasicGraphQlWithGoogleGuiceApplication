package org.deepak;


import com.google.inject.Guice;
import com.google.inject.Injector;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import org.deepak.handlers.GraphQLRequestHandler;

@Slf4j
public class GraphQLApplication {
    public static void main(String[] args) {

        // Create Guice injector with our custom module that defines all bindings
        Injector injector = Guice.createInjector(new GraphQLModule());


        // Create Javalin web server with JSON as default content type
        Javalin app = Javalin.create(
                config -> {
                    config.http.defaultContentType = "application/json";
                }
        );

        // Get GraphQL handler instance from Guice (with all dependencies injected)
        GraphQLRequestHandler handler = injector.getInstance(GraphQLRequestHandler.class);

        // Register POST endpoint for GraphQL queries
        app.post("/graphql", handler);

        // Start server on port 8080
        app.start(8080);
        log.info("Server started on http://localhost:8080/graphql");
    }
}