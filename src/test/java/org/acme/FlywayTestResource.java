package org.acme;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.eclipse.microprofile.config.ConfigProvider;
import org.flywaydb.core.Flyway;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

public class FlywayTestResource implements QuarkusTestResourceLifecycleManager {

    Logger Log = Logger.getLogger(this.getClass().getName());
    @Override
    public Map<String, String> start() {

        copyApplicationPropertiesToSystemProperties();

        var config = ConfigProvider.getConfig();
        var url = config.getValue("quarkus.datasource.jdbc.url", String.class);
        var username = config.getValue("quarkus.datasource.username", String.class);
        var password = config.getValue("quarkus.datasource.password", String.class);
        var cleanDisabled = !config.getValue("quarkus.flyway.clean-at-start", Boolean.class);

        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .cleanDisabled(cleanDisabled)
                .load();

        flyway.clean();
        flyway.migrate();

        return Map.of("some.service.url", "localhost:");
    }

    @Override
    public synchronized void stop() {
    }

    @Override
    public void inject(TestInjector testInjector) {
    }

    public void copyApplicationPropertiesToSystemProperties() {
        Path path = new File(getClass().getClassLoader().getResource("application.properties").getFile()).toPath();
        try (InputStream inputStream = Files.newInputStream(path)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> System.setProperty(k.toString() ,v.toString()));
            Log.info("Properties copied to environment variables successfully.");
            Log.info(System.getProperties().entrySet().stream().filter(entry -> entry.getKey().toString().startsWith("quarkus")).toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}