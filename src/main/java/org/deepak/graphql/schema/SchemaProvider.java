package org.deepak.graphql.schema;

import com.google.inject.Inject;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deepak.graphql.resolver.GetUserNameDataFetcher;
import org.deepak.graphql.resolver.HelloDataFetcher;

import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SchemaProvider {

    private final HelloDataFetcher helloDataFetcher;
    private final GetUserNameDataFetcher getUserNameDataFetcher;

    public GraphQLSchema buildSchema() {
        TypeDefinitionRegistry typeRegistry = parseSchema();
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring);
        return schema;
    }

    private TypeDefinitionRegistry parseSchema() {
        TypeDefinitionRegistry registry = new SchemaParser().parse(
                new InputStreamReader(Objects.requireNonNull(
                        getClass().getResourceAsStream("/schema.graphqls")
                ))
        );
        return registry;
    }
    private RuntimeWiring buildRuntimeWiring() {
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> {
                    return builder
                            .dataFetcher("hello", helloDataFetcher)
                            .dataFetcher("getUser", getUserNameDataFetcher);
                })
                .build();
        log.debug("Runtime wiring completed");
        return wiring;
    }
}
