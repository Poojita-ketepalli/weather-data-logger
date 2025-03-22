package com.weatherdata.integrationtests.config;


import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerConfig {

    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        mysqlContainer.start();
    }

    public static MySQLContainer<?> getMySQLContainer() {
        return mysqlContainer;
    }
}
