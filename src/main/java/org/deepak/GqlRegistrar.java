package org.deepak;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deepak.graphql.schema.SchemaProvider;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GqlRegistrar {
    private final GraphQL graphQL;

    @Inject
    public GqlRegistrar(SchemaProvider schemaProvider) {
        log.debug("Initializing GraphQL registrar");
        GraphQLSchema schema = schemaProvider.buildSchema();
        log.debug("Building GraphQL engine with schema");
        this.graphQL = GraphQL.newGraphQL(schema).build();
        log.info("GraphQL registrar initialized successfully");
    }

    public GraphQL getGraphQL() {
        log.debug("Returning GraphQL instance");
        return graphQL;
    }
}
