package org.deepak.graphql.resolver;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.deepak.generated.types.User;

@Slf4j
public class GetUserNameDataFetcher implements DataFetcher<User> {
    @Override
    public User get(DataFetchingEnvironment environment) throws Exception {
        log.debug("environment: {}", environment);
        log.debug("Executing GetUserNameDataFetcher for field: {}", environment.getField().getName());
        Integer id = environment.getArgument("id");
        log.debug("Fetching user with id: {}", id);

        // Use generated User class with builder pattern
        User user = User.newBuilder()
                .id(id)
                .name("User " + id)
                .build();

        log.debug("Returning user: {}", user);
        return user; // Return User object instead of empty string

    }
}
