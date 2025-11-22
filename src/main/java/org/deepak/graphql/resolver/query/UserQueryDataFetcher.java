package org.deepak.graphql.resolver.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.deepak.generated.types.User;

@Slf4j
public class UserQueryDataFetcher implements DataFetcher<User> {
    @Override
    public User get(DataFetchingEnvironment environment) throws Exception {
        log.debug("environment getVariables: {}", environment.getVariables());
        log.debug("environment getArguments: {}", environment.getArguments());
        log.debug("Executing UserQueryDataFetcher for field: {}", environment.getField().getName());
        Integer id = environment.getArgument("id");
        log.debug("Fetching user with id: {}", id);

        // Use generated User class with builder pattern
        User user = User.newBuilder()
                .id(id)
                .age(10)
                .build();

        log.debug("Returning user: {}", user);
        return user; // Return User object instead of empty string

    }
}
