package life.inha.icemarket;

import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.domain.auth.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class IceMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(IceMarketApplication.class, args);
    }
}