package com.agenda.commitment.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

/**
 * The Class AbstractIntegrationTest
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/08/2024
 */
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

        private static void startContainer() {
            Startables.deepStart(Stream.of(postgres)).join();
        }

        private static Map<String,String> createConnectionConfiguration(){
            return Map.of(
                    "spring.datasource.url",postgres.getJdbcUrl(),
                    "spring.datasource.username",postgres.getUsername(),
                    "spring.datasource.password",postgres.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            startContainer();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            MapPropertySource testcontainers =
                    new MapPropertySource("testcontainers",(Map)createConnectionConfiguration());

            environment.getPropertySources().addFirst(testcontainers);

        }
    }

}
