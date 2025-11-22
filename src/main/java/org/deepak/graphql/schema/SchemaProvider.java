package org.deepak.graphql.schema;

import com.google.inject.Inject;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deepak.graphql.resolver.field.UserNameDataFetcher;
import org.deepak.graphql.resolver.query.UserQueryDataFetcher;
import org.deepak.graphql.resolver.field.HelloDataFetcher;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SchemaProvider {

    private final HelloDataFetcher helloDataFetcher;
    private final UserQueryDataFetcher userQueryDataFetcher;
    private final UserNameDataFetcher userNameDataFetcher;

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

        List<TypeRuntimeWiring> runtimeWiringList = new ArrayList<>();

        // Add Query type data fetchers
        addDataFetcher(runtimeWiringList, "Query", "hello", helloDataFetcher);
        addDataFetcher(runtimeWiringList, "Query", "getUser", userQueryDataFetcher);

        // Add User type data fetchers
        addDataFetcher(runtimeWiringList, "User", "name", userNameDataFetcher);

        RuntimeWiring.Builder wiringBuilder = RuntimeWiring.newRuntimeWiring();
        createRunTimeWiringBuilder(runtimeWiringList, wiringBuilder);
        log.debug("Runtime wiring completed");
        return wiringBuilder.build();
    }
    private void addDataFetcher(List<TypeRuntimeWiring> runtimeWiringList, String typeName, String fieldName,
                                DataFetcher<?> dataFetcher) {
        runtimeWiringList.add(TypeRuntimeWiring.newTypeWiring(typeName)
                .dataFetcher(fieldName, dataFetcher)
                .build());
    }
    public static RuntimeWiring.Builder createRunTimeWiringBuilder(List<TypeRuntimeWiring> typeRuntimeWirings,
                                                                   final RuntimeWiring.Builder runtimeWiringBuilder) {
        typeRuntimeWirings.forEach(runtimeWiringBuilder::type);
        return runtimeWiringBuilder;
    }
}
