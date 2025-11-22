package org.deepak.graphql.resolver.field;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNameDataFetcher implements DataFetcher<String> {
    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        log.debug("environment getVariables: {}", environment.getVariables());
        log.debug("environment getArguments: {}", environment.getArguments());
        log.debug("environment source: {}", (Object) environment.getSource());
        log.debug("Executing UserNameDataFetcher for field: {}", environment.getField().getName());
        Integer id = (Integer) environment.getVariables().get("id");
        log.debug("Fetching user with id: {}", id);

        return "name " + id;

    }
}
