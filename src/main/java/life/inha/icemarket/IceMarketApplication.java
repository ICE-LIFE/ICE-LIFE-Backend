package life.inha.icemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IceMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(IceMarketApplication.class, args);
    }
}
