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
@RestController
@EnableJpaAuditing
public class IceMarketApplication {
    private final UserRepository userRepository;

    public IceMarketApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(IceMarketApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "id", defaultValue = "0") int id) {
        User user = userRepository.findById(id).orElseThrow();
        return String.format("Hello %s!", user.getName());
    }
}
