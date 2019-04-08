package io.viren.graphqldemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.viren.graphqldemo.repositories")
public class GraphqlDemoConfiguration {

}
