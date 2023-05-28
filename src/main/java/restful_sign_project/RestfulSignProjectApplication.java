package restful_sign_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class RestfulSignProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulSignProjectApplication.class, args);
	}
}

