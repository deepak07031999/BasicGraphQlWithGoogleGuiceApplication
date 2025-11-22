package org.deepak;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.deepak.graphql.schema.SchemaProvider;

@Slf4j
public class GraphQLModule extends AbstractModule {

    @Override
    protected void configure() {
        log.debug("Configuring GraphQL module dependencies");
    }

    @Provides
    @Singleton
    public GqlRegistrar provideGqlRegistrar(SchemaProvider provider) {
        log.debug("Creating GqlRegistrar instance");
        return new GqlRegistrar(provider);
    }

    @Provides
    @Singleton
    public GraphQL provideGraphQL(GqlRegistrar registrar) {
        log.debug("Providing GraphQL instance");
        GraphQL graphQL = registrar.getGraphQL();
        log.info("GraphQL engine initialized successfully");
        return graphQL;
    }
}
