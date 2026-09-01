package br.edu.ifpb.caveresearch.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "caveresearchPU";
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory();

    private JpaUtil() {
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("jakarta.persistence.jdbc.url", jdbcUrl());
        putIfEnvPresent(properties, "jakarta.persistence.jdbc.user", "POSTGRES_USER");
        putIfEnvPresent(properties, "jakarta.persistence.jdbc.password", "POSTGRES_PASSWORD");

        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
    }

    private static String jdbcUrl() {
        String host = envOrDefault("POSTGRES_HOST", "localhost");
        String port = envOrDefault("POSTGRES_PORT", "5432");
        String database = envOrDefault("POSTGRES_DB", "caveresearch");

        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }

    private static String envOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static void putIfEnvPresent(Map<String, String> properties, String propertyName, String environmentName) {
        String value = System.getenv(environmentName);
        if (value != null && !value.isBlank()) {
            properties.put(propertyName, value);
        }
    }
}
