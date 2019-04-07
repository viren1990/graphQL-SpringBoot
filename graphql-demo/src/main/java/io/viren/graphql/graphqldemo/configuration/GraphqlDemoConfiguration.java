package io.viren.graphql.graphqldemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.viren.graphql.graphqldemo.repositories")
public class GraphqlDemoConfiguration {

}
