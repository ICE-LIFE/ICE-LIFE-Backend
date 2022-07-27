package life.inha.icemarket;

import life.inha.icemarket.domain.core.User;
import life.inha.icemarket.respository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaAuditing
public class IceMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(IceMarketApplication.class, args);
    }
}
