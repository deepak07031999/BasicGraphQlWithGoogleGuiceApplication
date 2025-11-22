package org.deepak.graphql.resolver;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HelloDataFetcher implements  DataFetcher<String> {

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        log.debug("Executing HelloDataFetcher for field: {}", environment.getField().getName());
        String result = "Hello GraphQL from Guice + Javalin!";
        log.debug("HelloDataFetcher returning: {}", result);
        return result;
    }
}
