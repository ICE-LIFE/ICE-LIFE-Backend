package life.inha.icemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

public class DockerSecretPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String password_file = environment.getProperty("spring.datasource.password-file");
        if (password_file != null) {
            try {
                String password = String.join("", Files.readAllLines(Path.of(password_file)));
                Properties properties = new Properties();
                properties.put("spring.datasource.password", password);
                PropertySource<Map<String, Object>> propertySource = new PropertiesPropertySource("docker-secret", properties);
                environment.getPropertySources().addLast(propertySource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
